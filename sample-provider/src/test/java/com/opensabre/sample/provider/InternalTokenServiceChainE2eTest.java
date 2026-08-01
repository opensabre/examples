package com.opensabre.sample.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.Target;
import io.github.opensabre.security.config.InternalTokenProperties;
import io.github.opensabre.security.context.InternalTokenUserContext;
import io.github.opensabre.security.principal.SpringSecurityInternalTokenPrincipalProvider;
import io.github.opensabre.security.token.DefaultInternalTokenService;
import io.github.opensabre.security.token.InternalTokenConstants;
import io.github.opensabre.security.token.InternalTokenRequestFactory;
import io.github.opensabre.security.token.InternalTokenService;
import io.github.opensabre.security.webmvc.InternalTokenAuthenticationFilter;
import io.github.opensabre.rpc.openfeign.interceptor.FeignInternalTokenInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repeatable first-service -> Feign re-sign -> provider verification baseline.
 */
class InternalTokenServiceChainE2eTest {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void preservesRoleScopeAndDirectAuthorityAcrossServiceHop() throws Exception {
        InternalTokenProperties properties = properties("key-a", secret("key-a"));
        InternalTokenService tokenService = new DefaultInternalTokenService(
                new ObjectMapper(), properties);
        InternalTokenUserContext userContext = new InternalTokenUserContext(new ObjectMapper());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "operator",
                        null,
                        List.of(
                                new SimpleGrantedAuthority("ROLE_ADMIN"),
                                new SimpleGrantedAuthority("SCOPE_user.read"),
                                new SimpleGrantedAuthority("ORDER_WRITE"))));
        FeignInternalTokenInterceptor signer = new FeignInternalTokenInterceptor(
                tokenService,
                new InternalTokenRequestFactory(
                        userContext, new SpringSecurityInternalTokenPrincipalProvider()),
                properties,
                "sample-cloud");
        RequestTemplate template = new RequestTemplate();
        template.feignTarget(new Target.HardCodedTarget<>(
                SampleProviderClient.class, "sample-provider", "http://localhost"));
        template.header("Authorization", "Bearer external-jwt");

        signer.apply(template);

        assertThat(template.headers()).doesNotContainKey("Authorization");
        String internalToken = template.headers().get(InternalTokenConstants.HEADER)
                .iterator().next();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(InternalTokenConstants.HEADER, internalToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicReference<Authentication> downstreamAuthentication = new AtomicReference<>();
        filter(tokenService, properties).doFilter(request, response, (servletRequest, servletResponse) ->
                downstreamAuthentication.set(
                        SecurityContextHolder.getContext().getAuthentication()));

        assertThat(response.getStatus()).isEqualTo(200);
        Authentication authentication = downstreamAuthentication.get();
        assertThat(AuthorityAuthorizationManager.hasRole("ADMIN")
                .check(() -> authentication, null).isGranted()).isTrue();
        assertThat(AuthorityAuthorizationManager.hasAuthority("SCOPE_user.read")
                .check(() -> authentication, null).isGranted()).isTrue();
        assertThat(AuthorityAuthorizationManager.hasAuthority("ORDER_WRITE")
                .check(() -> authentication, null).isGranted()).isTrue();
    }

    @Test
    void rejectsMissingAmbiguousAndWrongIssuerCredentials() throws Exception {
        InternalTokenProperties properties = properties("key-a", secret("key-a"));
        InternalTokenService tokenService = new DefaultInternalTokenService(
                new ObjectMapper(), properties);
        InternalTokenAuthenticationFilter filter = filter(tokenService, properties);

        MockHttpServletResponse missingResponse = new MockHttpServletResponse();
        filter.doFilter(new MockHttpServletRequest(), missingResponse, new MockFilterChain());
        assertThat(missingResponse.getStatus()).isEqualTo(401);

        String token = issueServiceToken(tokenService, properties);
        MockHttpServletRequest ambiguous = new MockHttpServletRequest();
        ambiguous.addHeader(InternalTokenConstants.HEADER, token);
        ambiguous.addHeader("Authorization", "Bearer external-jwt");
        MockHttpServletResponse ambiguousResponse = new MockHttpServletResponse();
        filter.doFilter(ambiguous, ambiguousResponse, new MockFilterChain());
        assertThat(ambiguousResponse.getStatus()).isEqualTo(401);
        assertThat(ambiguousResponse.getErrorMessage()).isEqualTo("AMBIGUOUS_CREDENTIALS");

        InternalTokenProperties rejectingProperties = properties("key-a", secret("key-a"));
        rejectingProperties.setAllowedIssuers(Set.of("another-service"));
        MockHttpServletRequest wrongIssuer = new MockHttpServletRequest();
        wrongIssuer.addHeader(InternalTokenConstants.HEADER, token);
        MockHttpServletResponse wrongIssuerResponse = new MockHttpServletResponse();
        filter(new DefaultInternalTokenService(new ObjectMapper(), rejectingProperties),
                rejectingProperties)
                .doFilter(wrongIssuer, wrongIssuerResponse, new MockFilterChain());
        assertThat(wrongIssuerResponse.getStatus()).isEqualTo(401);
        assertThat(wrongIssuerResponse.getErrorMessage()).isEqualTo("INVALID_ISSUER");
    }

    @Test
    void acceptsOldAndNewKeysDuringRotation() {
        InternalTokenProperties oldProperties = properties("key-a", secret("key-a"));
        String oldToken = issueServiceToken(
                new DefaultInternalTokenService(new ObjectMapper(), oldProperties), oldProperties);
        InternalTokenProperties rotatedProperties = properties("key-b", secret("key-b"));
        rotatedProperties.setPreviousKeyId("key-a");
        rotatedProperties.setPreviousKey(secret("key-a"));
        InternalTokenService rotatedService = new DefaultInternalTokenService(
                new ObjectMapper(), rotatedProperties);
        String newToken = issueServiceToken(rotatedService, rotatedProperties);

        assertThat(rotatedService.verify(oldToken, "sample-provider").subject())
                .isEqualTo("service:sample-cloud");
        assertThat(rotatedService.verify(newToken, "sample-provider").subject())
                .isEqualTo("service:sample-cloud");
    }

    private static String issueServiceToken(
            InternalTokenService tokenService,
            InternalTokenProperties properties) {
        FeignInternalTokenInterceptor signer = new FeignInternalTokenInterceptor(
                tokenService,
                new InternalTokenRequestFactory(new InternalTokenUserContext(new ObjectMapper())),
                properties,
                "sample-cloud");
        RequestTemplate template = new RequestTemplate();
        template.feignTarget(new Target.HardCodedTarget<>(
                SampleProviderClient.class, "sample-provider", "http://localhost"));
        signer.apply(template);
        return template.headers().get(InternalTokenConstants.HEADER).iterator().next();
    }

    private static InternalTokenAuthenticationFilter filter(
            InternalTokenService tokenService,
            InternalTokenProperties properties) {
        properties.setRequired(true);
        return new InternalTokenAuthenticationFilter(
                tokenService,
                new InternalTokenUserContext(new ObjectMapper()),
                properties,
                "sample-provider");
    }

    private static InternalTokenProperties properties(String keyId, String key) {
        InternalTokenProperties properties = new InternalTokenProperties();
        properties.setEnabled(true);
        properties.setActiveKeyId(keyId);
        properties.setActiveKey(key);
        properties.setAllowedIssuers(Set.of("sample-cloud"));
        return properties;
    }

    private static String secret(String value) {
        byte[] bytes = (value + "-0123456789abcdef0123456789abcdef")
                .getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private interface SampleProviderClient {
    }
}
