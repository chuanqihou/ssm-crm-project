package com.chuanqihou.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private Long id;
  private String customerName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthday;
  private String sex;
  private String tel;
  private Double sal;
  private String profession;
  private String address;
  private Long deptId;

  private Dept dept;

}
