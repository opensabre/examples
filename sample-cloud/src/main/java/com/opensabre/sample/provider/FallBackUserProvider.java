package com.opensabre.sample.provider;

import com.google.common.collect.Maps;
import io.github.opensabre.common.core.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * user降级实例
 */
@Slf4j
@Component
public class FallBackUserProvider implements UserProvider {
    @Override
    public Result<Map<String, String>> getUser(String param) {
        Map<String, String> userMap = Maps.newHashMap();
        userMap.put("id", param);
        userMap.put("name", RandomStringUtils.randomAscii(10));
        log.error("fallback getUser param:{}", param);
        return Result.success(userMap);
    }
}
