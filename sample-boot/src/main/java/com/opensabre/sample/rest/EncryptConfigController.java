package com.opensabre.sample.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Tag(name = "config")
@RequestMapping("/config")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
public class EncryptConfigController {

    @Value("${test.password}")
    private String passwd;

    @Resource
    private StringEncryptor stringEncryptor;

    @Operation(summary = "配置项加密验证", description = "配置项加密")
    @GetMapping("/get")
    public String config() {
        log.info("plain:{}", passwd);
        return passwd;
    }
}

