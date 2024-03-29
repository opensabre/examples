package com.opensabre.sample.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "test")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
@Slf4j
@RestController
@RequestMapping("/test")
public class HelloController {

    @Operation(summary = "测试接口1", description = "hello xxx")
    @GetMapping("/echo")
    public String echo(@RequestParam String name) {
        return "Hello:" + name;
    }

    @Operation(summary = "测试接口2", description = "hello xxx")
    @GetMapping("/hello")
    public int hello(@RequestParam Integer number) {
        return number;
    }

    @Operation(summary = "测试接口3", description = "hello xxx")
    @GetMapping("/map/{param}")
    public Map<String, String> map(@PathVariable String param) {
        Map<String, String> newHashMap = Maps.newHashMap();
        newHashMap.put("num", RandomStringUtils.randomAlphabetic(15));
        newHashMap.put(param, UUID.randomUUID().toString());
        return newHashMap;
    }

    @Operation(summary = "测试接口4", description = "hello xxx")
    @GetMapping("/list")
    public List<String> list() {
        return Lists.newArrayList("1", "34", "abc");
    }
}
