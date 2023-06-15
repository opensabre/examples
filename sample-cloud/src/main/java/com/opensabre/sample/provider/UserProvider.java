package com.opensabre.sample.provider;

import io.github.opensabre.common.core.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "sample-cloud")
public interface UserProvider {

    @GetMapping("test/map/{param}")
    Result<Map<String, String>> getUser(@PathVariable String param);
}

