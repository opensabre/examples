package com.opensabre.sample.entity;

import io.github.opensabre.boot.annotations.Desensitization;
import io.github.opensabre.boot.sensitive.rest.DesensitizationTypeEnum;
import io.github.opensabre.common.web.entity.vo.BaseVo;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseVo {
    @Desensitization(type = DesensitizationTypeEnum.USER_ID)
    private long userId;
    @Desensitization(type = DesensitizationTypeEnum.MOBILE_PHONE)
    private String mobile;
    @Desensitization(type = DesensitizationTypeEnum.CHINESE_NAME)
    private String name;
    @Desensitization(type = DesensitizationTypeEnum.EMAIL)
    private String email;
    @Desensitization(type = DesensitizationTypeEnum.ADDRESS)
    private String address;
    @Desensitization(type = DesensitizationTypeEnum.PASSWORD)
    private String password;
    @Desensitization(type = DesensitizationTypeEnum.CUSTOM, startInclude = 2, endExclude = 4)
    private String orderId;
}