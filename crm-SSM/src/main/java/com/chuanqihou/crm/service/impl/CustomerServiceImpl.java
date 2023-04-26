package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.mapper.CustomerMapper;
import com.chuanqihou.crm.mapper.DeptMapper;
import com.chuanqihou.crm.service.CustomerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/25 18:49
 * @description 客户业务实现类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 保存客户信息
     * @param customerDto   客户参数
     * @return  result
     */
    @Override
    @Transactional
    public Result saveCustomer(CustomerDto customerDto) {
        //判断部门编号是否存在
        int deptIsExist = deptMapper.selectDeptIsExist(customerDto);
        if (deptIsExist == 0) {
            return new Result(-1, "部门编号不存在!");
        }
        //判断该客户是否存在
        int customerIsExist = customerMapper.selectCustomerIsExist(customerDto);
        if (customerIsExist != 0) {
            return new Result(-1, "客户已存在!");
        }
        //保存客户信息
        int insertUser = customerMapper.insertUser(customerDto);
        if (insertUser == 0) {
            return new Result(-1, "保存客户信息失败，请联系管理员！");
        }
        return new Result();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Result findCustomerByPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Customer> customers = customerMapper.selectCustomerByPage();

        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);

        return new Result(200,"success",customerPageInfo.getList(),customerPageInfo.getTotal());
    }
}
