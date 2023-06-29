package com.opensabre.sample.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensabre.sample.entity.UserVo;
import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/map")
    public Map<String, String> map() {
        Map<String, String> newHashMap = Maps.newHashMap();
        newHashMap.put("test", "123");
        return newHashMap;
    }

    @Operation(summary = "测试接口4", description = "hello xxx")
    @GetMapping("/list")
    public List<String> list() {
        return Lists.newArrayList("1", "34", "abc");
    }

    @Operation(summary = "测试接口4", description = "hello xxx")
    @GetMapping("/user")
    public UserVo user() throws JsonProcessingException {
        UserVo userVo = UserVo.builder()
                .userId(1234567890)
                .name("张三")
                .mobile("13711220098")
                .email("abc123@123.com")
                .password("1qazXSW@")
                .orderId("A76883636636")
                .address("上海市浦东新区上丰路1234号1栋2509室")
                .build();
        log.info("userVo:{}", new ObjectMapper().writeValueAsString(userVo));
        log.info("userVo email:{}, mobile={}", userVo.getEmail(), userVo.getMobile());
        return userVo;
    }

}
