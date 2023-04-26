package com.chuanqihou.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

  @NotNull(message = "客户姓名必填")
  @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$",message = "客户姓名必须为中文，且长度在2-10位")
  private String customerName;

  @NotNull(message = "生日必填")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",message = "生日格式必须为：yyyy-MM-dd 如2001-01-01")
  private String birthday;

  @NotNull(message = "性别必填")
  @Pattern(regexp = "^[01]$",message = "性别格式错误！女：0 男：1")
  private String sex;

  @NotNull(message = "联系电话必填")
  @Pattern(regexp = "^(\\+?0?86\\-?)?1[3456789]\\d{9}$",message = "手机号格式错误，必须为中国大陆手机号")
  private String tel;

  @NotNull(message = "薪资必填")
  private Double sal;

  @NotNull(message = "职位必填")
  @Pattern(regexp = "^[123]$",message = "职位格式错误！工程师：1 程序员：2 码农：3")
  private String profession;

  @NotNull(message = "地址必填")
  private String address;

  @NotNull(message = "部门编号必填")
  private Long deptId;

}
