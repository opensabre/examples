package io.github.opensabre.sample.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "test")
@Slf4j
public class HelloController {


    @Operation(summary = "测试接口", description = "hello xxx")
    @GetMapping("/hello")
    public Result hello(@RequestParam String name) {
        return Result.success("Hello:" + name);
    }
}
