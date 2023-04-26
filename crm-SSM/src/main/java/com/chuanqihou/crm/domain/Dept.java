package com.chuanqihou.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {

  private Long id;
  private String name;
  private String loc;

  private List<Customer> customers;

}
