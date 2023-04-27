package com.chuanqihou.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date createTime;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date updateTime;

}
