package io.github.opensabre.sample.form;

import io.github.opensabre.common.web.entity.form.BaseForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@Schema
@ToString
@EqualsAndHashCode(callSuper = true)
public class ValidForm extends BaseForm {
    @NotNull(message = "id不能为空", groups = {Add.class, Save.class})
    private int id;

    @NotBlank(message = "⽤户不能为空!", groups = {Add.class, Save.class})
    @Schema(title = "用户名")
    private String name;

    @Schema(title = "用户密码")
    @NotBlank(message = "⽤户密码不能为空!", groups = Add.class)
    @Size(min = 3, max = 20, message = "密码为3~20字母数字", groups = Add.class)
    private String password;

    @Schema(title = "用户邮箱")
    @NotBlank(message = "⽤户邮箱不能为空!", groups = {Add.class})
    @Email(message = "请输入正确的邮箱地址，如 abc123@qq.com", groups = {Add.class, Save.class})
    private String email;

    @Min(value = 18, message = "年龄不能小于18周岁", groups = {Add.class})
    @Max(value = 120, message = "年龄不能大于120周岁", groups = {Add.class})
    private int age;

    @Past
    private Date time;

    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", groups = {Add.class}, message = "请输入正确的手机号")
    private String mobile;

    /**
     * 新增
     */
    public interface Add {
    }

    /**
     * 修改
     */
    public interface Save {
    }
}

