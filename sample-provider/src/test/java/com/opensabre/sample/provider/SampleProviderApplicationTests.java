package com.opensabre.sample.provider;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.nacos.config.enabled=false",
        "spring.cloud.nacos.discovery.enabled=false",
        "opensabre.governance.enabled=false"
})
class SampleProviderApplicationTests {

    @Test
    void contextLoads() {
    }

}
