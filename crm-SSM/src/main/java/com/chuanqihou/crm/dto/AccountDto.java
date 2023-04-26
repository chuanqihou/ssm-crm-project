package com.chuanqihou.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author 传奇后
 * @date 2023/4/26 16:09
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull(message = "账户名必填")
    @Pattern(regexp = "\\w{4,20}",message = "账户名必须为4到20个字符的任意字母、数字或下划线")
    private String username;
    @NotNull(message = "密码必填")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).{6,12}$",message = "密码必须包含大写字母、小写字母、特殊符号，且长度6-12")
    private String pwd;
    @NotNull(message = "验证码必填")
    @Pattern(regexp = "[0-9a-zA-Z]{4}",message = "验证码必须满足长度为4的数字或字母（不区分大小写）")
    private String captcha;

}
