package com.chuanqihou.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * @author 传奇后
 * @date 2023/4/26 14:08
 * @description 客户搜索传回的实体【参数】
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchDto extends BaseDto {

    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$",message = "客户姓名必须为中文，且长度在2-10位")
    private String customerName;

    @Pattern(regexp = "^[01]$",message = "性别格式错误！女：0 男：1")
    private String sex;

    @Pattern(regexp = "^(\\+?0?86\\-?)?1[3456789]\\d{9}$",message = "手机号格式错误，必须为中国大陆手机号")
    private String tel;

    private String deptName;

}
