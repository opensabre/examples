package io.github.opensabre.sample.cache.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RemoteProvider {

    public String getRemote(String key) {
        log.info("load from remote : {}", key);
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            log.error("remote invoke error", e);
        }
        return key + ":" + RandomStringUtils.randomGraph(5);
    }
}
