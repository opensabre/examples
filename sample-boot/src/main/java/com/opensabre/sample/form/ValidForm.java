package com.opensabre.sample.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.opensabre.common.web.entity.form.BaseForm;
import io.github.opensabre.common.web.validator.EnumString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@Schema
@ToString
@EqualsAndHashCode(callSuper = true)
public class ValidForm extends BaseForm {

    @Schema(title = "用户ID")
    @NotNull(message = "id不能为空", groups = {Add.class, Save.class})
    private int id;

    @Schema(title = "用户名")
    @NotBlank(message = "⽤户不能为空!", groups = {Add.class, Save.class})
    private String name;

    @Schema(title = "用户密码")
    @NotBlank(message = "⽤户密码不能为空!", groups = Add.class)
    @Size(min = 3, max = 20, message = "密码为3~20字母数字", groups = Add.class)
    private String password;

    @Schema(title = "用户邮箱")
    @NotBlank(message = "⽤户邮箱不能为空!", groups = {Add.class, Save.class})
    @Email(message = "请输入正确的邮箱地址，如 abc123@qq.com", groups = {Add.class, Save.class})
    private String email;

    @Schema(title = "年龄")
    @Min(value = 18, message = "年龄不能小于18周岁", groups = {Add.class})
    @Max(value = 120, message = "年龄不能大于120周岁", groups = {Add.class})
    private int age;

    @Schema(title = "时间", example = "2023-02-22 14:56:10")
    @Past(groups = {Add.class, Save.class}, message = "时间格式为yyyy-MM-dd HH:mm:ss，并且不能为将来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @Schema(title = "手机号")
    @Pattern(regexp = "^1[3456789]\\d{9}$", groups = {Add.class}, message = "请输入正确的手机号")
    private String mobile;

    @Schema(title = "类型")
    @EnumString(message = "类型只能为Last、Second、First", value = {"Last", "Second", "First"}, groups = {Add.class, Save.class})
    private String type;

    /**
     * 新增校验分组
     */
    public interface Add {
    }

    /**
     * 修改校验分组
     */
    public interface Save {
    }
}

