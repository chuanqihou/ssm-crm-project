package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;
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
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //查询数据
        List<Customer> customers = customerMapper.selectCustomerByPage();
        //获得pageInfo对象
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        //返回结果
        return new Result(200,"success",customerPageInfo.getList(),customerPageInfo.getTotal());
    }

    /**
     * 根据客户id删除单条客户信息
     * @param id 客户id
     * @return  result
     */
    @Override
    public Result removeCustomer(Long id) {
        //数据效验
        if (id==null || id < 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //执行删除
        int deleteCustomer = customerMapper.deleteCustomer(id);
        if (deleteCustomer == 0) {
            return new Result(-1, "删除失败");
        }
        return new Result();
    }

    /**
     * 根据客户id批量删除
     * @param ids   客户id 【格式：1,2,3】
     * @return  result
     */
    @Override
    public Result removeManyCustomer(String ids) {
        //数据效验
        if (ids==null || !ids.matches("^\\d+(,\\d+)*$")){
            return Result.DATE_FORMAT_ERROR;
        }
        //执行删除
        int deleteManyCustomer = customerMapper.deleteManyCustomer(ids);
        //判断删除结果
        if (deleteManyCustomer == 0) {
            return new Result(-1, "删除失败");
        }
        //返回结果
        return new Result();
    }


    /**
     * 根据条件动态搜索
     * @param customerSearchDto 搜索条件实体
     * @return  result
     */
    @Override
    public Result findCustomerBySearch(CustomerSearchDto customerSearchDto) {
        //开启分页
        PageHelper.startPage(customerSearchDto.getPageNum(), customerSearchDto.getPageSize());
        //查询数据
        List<Customer> customers = customerMapper.selectCustomersBySearch(customerSearchDto);
        //加入分页
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        //返回数据
        return new Result(200,"success",customerPageInfo.getList(),customerPageInfo.getTotal());
    }

    /**
     * 修改客户信息
     * @param customerDto
     * @return
     */
    @Override
    public Result modifyCustomer(CustomerDto customerDto) {
        //判断部门编号是否存在
        int customerIsExist = deptMapper.selectDeptIsExist(customerDto);
        if (customerIsExist == 0) {
            return new Result(-1, "部门不存在");
        }
        //判断客户是否修改
        int i = customerMapper.selectCustomerIsExist(customerDto);
        if (i != 0) {
            return new Result(-1, "请修改");
        }
        //执行修改
        int updateCustomer = customerMapper.updateCustomer(customerDto);
        if (updateCustomer == 0) {
            return new Result(-1, "修改失败");
        }
        //返回结果
        return new Result();
    }
}
