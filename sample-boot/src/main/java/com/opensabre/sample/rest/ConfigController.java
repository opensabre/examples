package com.opensabre.sample.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "配置项测试")
@RequestMapping("/config")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
public class ConfigController {

    @Value("${test.password}")
    private String passwd;

    @Resource
    private StringEncryptor stringEncryptor;

    @Operation(summary = "加密配置项验证", description = "加密配置项获取")
    @GetMapping("/get")
    public String configEncrypt() {
        log.info("plain:{}", passwd);
        return passwd;
    }

    @Operation(summary = "配置项获取", description = "配置项获取")
    @PostMapping("/get")
    public String config(@RequestParam("key") String key) {
        String propertyValue = System.getProperty(key);
        log.info("config:{}", propertyValue);
        return propertyValue;
    }
}

