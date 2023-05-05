package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 传奇后
 * @date 2023/4/25 17:07
 * @description customer 映射接口
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

    /**
     * 根据条件查询客户信息
     * @param customerSearchDto 封装条件的实体
     * @return  客户信息的集合
     */
    List<Customer> selectCustomersBySearch(CustomerSearchDto customerSearchDto);


    /**
     * 更新客户信息
     * @param customerDto   客户信息参数
     * @return 更新结果
     */
    int updateCustomer(CustomerDto customerDto);

    /**
     * 统计性别
     * @return list<Map<String,String>
     */
    List<Map<String, String>> selectSexCount();

}
