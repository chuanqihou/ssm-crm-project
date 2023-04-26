package com.chuanqihou.crm.service;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.CustomerDto;

/**
 * @author 传奇后
 * @date 2023/4/25 18:48
 * @description
 */
public interface CustomerService {

    Result saveCustomer(CustomerDto customerDto);

    Result findCustomerByPage(Integer pageNum, Integer pageSize);

    Result removeCustomer(Long id);

    Result removeManyCustomer(String ids);

}
