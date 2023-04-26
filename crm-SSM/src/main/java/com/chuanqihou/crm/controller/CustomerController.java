package com.chuanqihou.crm.controller;
import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;
import com.chuanqihou.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/25 19:16
 * @description 客户控制器
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 添加客户控制器
     * @param customerDto   客户信息
     * @param bindingResult
     * @return
     */
    @PostMapping("/addCustomer.do")
    public Result addCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
        //数据效验
        if (bindingResult.hasErrors()) {
            //取出所有
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                //返回第一个
                return new Result(-1, fieldError.getDefaultMessage());
            }
        }
        //调用业务层并返回
        return customerService.saveCustomer(customerDto);
    }

    /**
     * 分页查询客户信息
     * @param pageNum   第几页
     * @param pageSize  每页几条数据【非必须，默认值为5】
     * @return  result
     */
    @PostMapping("/getCustomerByPage.do")
    public Result getCustomerByPage(Integer pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize) {
        return customerService.findCustomerByPage(pageNum, pageSize);
    }

    /**
     * 单条删除
     * @param id
     * @return
     */
    @PostMapping("/cutOneCustomer.do")
    public Result cutOneCustomer(Long id) {
        return customerService.removeCustomer(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/cutManyCustomer.do")
    public Result cutOneCustomer(String ids) {
        return customerService.removeManyCustomer(ids);
    }

    /**
     * 根据条件查询搜索customer
     * @param customerSearchDto 条件实体
     * @param bindingResult
     * @return
     */
    @PostMapping("/getCustomerBySearch.do")
    public Result getCustomerBySearch(@Valid CustomerSearchDto customerSearchDto,BindingResult bindingResult) {
        //数据效验
        if (bindingResult.hasErrors()) {
            //取出所有
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                //返回第一个
                return new Result(-1, fieldError.getDefaultMessage());
            }
        }
        //调用业务方法并返回结果
        return customerService.findCustomerBySearch(customerSearchDto);
    }

}
