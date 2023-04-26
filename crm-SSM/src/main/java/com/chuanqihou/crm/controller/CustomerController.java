package com.chuanqihou.crm.controller;
import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
