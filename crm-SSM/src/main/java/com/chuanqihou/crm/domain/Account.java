package com.chuanqihou.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  private Long id;
  private String username;
  private String pwd;
  private String imgUrl;
  private String createTime;
  private String updateTime;

}
