package com.opensabre.sample.config;

import io.github.opensabre.boot.sensitive.log.desensitizer.LogBackDesensitizer;
import io.github.opensabre.boot.sensitive.log.desensitizer.NameLogBackDesensitizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensitiveConfig {

    @Bean
    LogBackDesensitizer nameLogBackDesensitizer() {
        return new NameLogBackDesensitizer();
    }
}
