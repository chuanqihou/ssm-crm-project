package com.chuanqihou.crm.service;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/25 18:48
 * @description
 */
public interface CustomerService {

    /**
     * 保存客户信息
     * @param customerDto
     * @return
     */
    Result saveCustomer(CustomerDto customerDto);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findCustomerByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据客户id删除单条客户信息
     * @param id
     * @return
     */
    Result removeCustomer(Long id);

    /**
     * 根据客户id批量删除
     * @param ids
     * @return
     */
    Result removeManyCustomer(String ids);

    /**
     * 根据条件动态搜索
     * @param customerSearchDto
     * @return
     */
    Result findCustomerBySearch(CustomerSearchDto customerSearchDto);

    /**
     * 修改客户信息
     * @param customerDto
     * @return
     */
    Result modifyCustomer(CustomerDto customerDto);


    /**
     * 导出excel
     */
    void exportExcel(List<Customer> customerList, OutputStream out) throws IOException;

    /**
     * 性别统计
     */
    Result findSexCount();

}
