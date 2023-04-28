package com.chuanqihou.crm.controller;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.AccountDto;
import com.chuanqihou.crm.dto.BaseDto;
import com.chuanqihou.crm.service.AccountService;
import com.chuanqihou.crm.util.DataValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 传奇后
 * @date 2023/4/26 15:17
 * @description
 */
@Transactional
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 用户登录控制器
     * @param accountDto    登录参数实体
     * @param bindingResult 绑定结果
     * @return  result
     */
    @PostMapping("/getAccountByLogin.do")
    public Result getAccountByLogin(@Valid AccountDto accountDto, BindingResult bindingResult) {
        Result result = DataValidUtil.dataValid(bindingResult);
        if (result != null) {
            return result;
        }
        return accountService.findAccountByLogin(accountDto);
    }

    /**
     * 分页查询账户信息
     * @param baseDto   参数
     * @return  result
     */
    @PostMapping("/getAccountByPage.do")
    public Result getAccountByPage(BaseDto baseDto) {
        //调用业务层方法并返回
        return accountService.findAccountByPage(baseDto);
    }

    /**
     * 根据id删除账户【单个删除】
     * @param id 需要删除的账户id
     * @return  result
     */
    @PostMapping("/cutOneAccount.do")
    public Result cutOneAccount(Long id) {
        //调用业务层方法并返回
        return accountService.removeAccountById(id);
    }

    /**
     * 重置密码控制器
     * @param id 账户Id
     * @return result
     */
    @PostMapping("/editAccountPwd.do")
    public Result editAccountPwd(Long id) {
        //调用业务层方法并返回
        return accountService.modifyResetPwdById(id);
    }

    @PostMapping("/addAccount.do")
    public Result addAccount(String username) {
        //调用业务层方法并返回
        return accountService.saveAccount(username);
    }

}