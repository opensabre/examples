package com.opensabre.sample.entity;

import io.github.opensabre.boot.annotations.Desensitization;
import io.github.opensabre.boot.security.sensitive.DesensitizationTypeEnum;
import io.github.opensabre.common.web.entity.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Desensitization(type = DesensitizationTypeEnum.CUSTOMER, startInclude = 2, endExclude = 4)
    private String orderId;
}
