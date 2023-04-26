package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.dto.CustomerDto;
/**
 * @author 传奇后
 * @date 2023/4/25 17:07
 * @description
 */
public interface CustomerMapper {

    /**
     * 查询客户是否存在【条件：客户姓名、生日、电话、性别】
     * @param customerDto
     * @return
     */
    int selectCustomerIsExist(CustomerDto customerDto);

    /**
     * 新增客户
     * @param customerDto
     * @return
     */
    int insertUser(CustomerDto customerDto);

}
