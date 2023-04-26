package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 分页查询
     * @return
     */
    List<Customer> selectCustomerByPage();

    /**
     * 单条删除
     * 根据id删除客户信息
     * @param id    客户id
     * @return
     */
    int deleteCustomer(Long id);

    /**
     * 批量删除客户信息
     * @param ids   格式【1，2，3】
     * @return
     */
    int deleteManyCustomer(@Param("ids") String ids);

}
