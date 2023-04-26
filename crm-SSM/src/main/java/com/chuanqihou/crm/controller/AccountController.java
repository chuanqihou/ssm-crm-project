package com.chuanqihou.crm.controller;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.AccountDto;
import com.chuanqihou.crm.service.AccountService;
import com.chuanqihou.crm.util.DataValidUtil;
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
 * @date 2023/4/26 15:17
 * @description
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/getAccountByLogin.do")
    public Result getAccountByLogin(@Valid AccountDto accountDto, BindingResult bindingResult) {
        Result result = DataValidUtil.dataValid(bindingResult);
        if (result != null) {
            return result;
        }
        return accountService.findAccountByLogin(accountDto);
    }

}