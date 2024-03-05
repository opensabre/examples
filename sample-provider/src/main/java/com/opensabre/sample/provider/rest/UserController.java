package com.opensabre.sample.provider.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "user")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Operation(summary = "用户管理", description = "hello user")
    @GetMapping("/{userId}")
    public Map<String, String> get(@PathVariable("userId") String userId) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", userId);
        userMap.put("name", RandomStringUtils.randomAlphabetic(5));
        log.info("get user:{}", userMap);
        return userMap;
    }
}