package com.opensabre.sample.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Tag(name = "权限测试")
@RequestMapping("/auth")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
public class AuthorityController {

    @Operation(summary = "返回String", description = "String")
    @GetMapping("/echo")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public String echo(@RequestParam("name") String name) {
        return "Hello:" + name;
    }

    @PreAuthorize("hasAuthority('app')")
    @Operation(summary = "返回int", description = "int")
    @GetMapping("/hello")
    public int hello(@RequestParam("number") Integer number) {
        return number;
    }
}
