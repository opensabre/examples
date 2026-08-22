package com.opensabre.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.nacos.config.enabled=false",
        "spring.cloud.nacos.discovery.enabled=false",
        "opensabre.governance.enabled=false"
})
class SampleApplicationTests {

    @Test
    void contextLoads() {
    }
}
