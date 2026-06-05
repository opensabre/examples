package com.opensabre.sample.webflux.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.github.opensabre.common.core.exception.BaseException;
import io.github.opensabre.common.core.exception.SystemErrorType;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/webflux")
public class HelloController {

    @GetMapping("/hello/{name}")
    @SuppressWarnings("unchecked")
    public Mono<Result<Map<String, String>>> hello(@PathVariable String name) {
        return Mono.just(Result.success(Map.of("message", "hello " + name)));
    }

    @GetMapping("/users/{userId}")
    @SuppressWarnings("unchecked")
    public Mono<Result<Map<String, Integer>>> user(@PathVariable @Min(value = 1, message = "userId must be greater than 0") Integer userId) {
        return Mono.just(Result.success(Map.of("userId", userId)));
    }

    @GetMapping("/busy")
    public Mono<Result<Void>> busy() {
        return Mono.error(new BaseException(SystemErrorType.SYSTEM_BUSY));
    }
}
