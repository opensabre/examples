package io.github.opensabre.sample.rest;

import io.github.opensabre.common.core.entity.vo.Result;
import io.github.opensabre.sample.form.ValidForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "valid")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidController {

    @Operation(summary = "数据校验接口1", description = "Form表单校验")
    @PostMapping("/form")
    public String formAdd(@Parameter(description = "表单校验", required = true) @Validated(ValidForm.Add.class) @RequestBody ValidForm validForm) {
        log.info("form is {}", validForm.getName());
        return validForm.toString();
    }
}
