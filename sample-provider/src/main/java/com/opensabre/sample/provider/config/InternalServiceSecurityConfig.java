package com.opensabre.sample.provider.config;

import io.github.opensabre.security.webmvc.InternalTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Downstream Servlet service: accepts signed internal tokens and rejects anonymous calls.
 */
@Configuration(proxyBeanMethods = false)
public class InternalServiceSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            InternalTokenAuthenticationFilter internalTokenAuthenticationFilter)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**", "/v3/**", "/doc.html", "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(
                        internalTokenAuthenticationFilter,
                        AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
