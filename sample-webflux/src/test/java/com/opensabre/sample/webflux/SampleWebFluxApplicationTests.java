package com.opensabre.sample.webflux;

import io.github.opensabre.common.core.entity.vo.Result;
import io.github.opensabre.common.core.exception.SystemErrorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SampleWebFluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void helloShouldReturnReactiveResult() {
        webTestClient.get()
                .uri("/webflux/hello/opensabre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(Result.SUCCESSFUL_CODE)
                .jsonPath("$.data.message").isEqualTo("hello opensabre");
    }

    @Test
    void invalidPathVariableShouldUseWebFluxStarterAdvice() {
        webTestClient.get()
                .uri("/webflux/users/abc")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(SystemErrorType.ARGUMENT_NOT_VALID.getCode());
    }

    @Test
    void baseExceptionShouldUseWebFluxStarterAdvice() {
        webTestClient.get()
                .uri("/webflux/busy")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(SystemErrorType.SYSTEM_BUSY.getCode());
    }
}
