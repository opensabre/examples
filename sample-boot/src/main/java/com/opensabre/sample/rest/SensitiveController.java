package com.opensabre.sample.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "脱敏测试")
@RequestMapping("/sensitive")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
public class SensitiveController {

    @Operation(summary = "脱敏测试接口", description = "脱敏测试")
    @GetMapping("/user")
    public UserVo user() throws JsonProcessingException {
        UserVo userVo = UserVo.builder()
                .userId(1234567890)
                .name("张三一")
                .phone("0571-1234567")
                .mobile("13711220098")
                .email("abc123@123.com")
                .password("1qazXSW@")
                .orderId("A76883636636")
                .address("上海市浦东新区上丰路1234号1栋2509室")
                .build();
        log.info("userVo:{}", new ObjectMapper().writeValueAsString(userVo));
        log.info("userVo email:{}, mobile={} ,phone={}", userVo.getEmail(), userVo.getMobile(), userVo.getPhone());
        log.info("userVo password:{} , userVo passwd:{} key is {}", userVo.getPassword(), "1qaz@WSX", "key123");
        log.info("name is 张四一，姓名：李四六，住址：{}", "中国台湾省台北市新竹县桃源村5号天下花园1幢103室");
        return userVo;
    }
}