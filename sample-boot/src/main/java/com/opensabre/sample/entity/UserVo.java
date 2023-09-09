package com.opensabre.sample.entity;

import io.github.opensabre.boot.annotations.Desensitization;
import io.github.opensabre.common.web.entity.vo.BaseVo;
import lombok.*;

import static io.github.opensabre.boot.sensitive.rule.DefaultSensitiveRule.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseVo {
    private long userId;
    @Desensitization(type = MOBILE)
    private String mobile;
    @Desensitization(type = PHONE)
    private String phone;
    @Desensitization(type = NAME)
    private String name;
    @Desensitization(type = EMAIL)
    private String email;
    @Desensitization(type = ADDRESS)
    private String address;
    @Desensitization(type = PASSWORD)
    private String password;
    @Desensitization(type = CUSTOM, retainPrefixCount = 2, retainSuffixCount = 4)
    private String orderId;
}