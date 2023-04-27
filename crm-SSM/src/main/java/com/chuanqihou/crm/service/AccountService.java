package com.chuanqihou.crm.service;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.AccountDto;
import com.chuanqihou.crm.dto.BaseDto;

/**
 * @author 传奇后
 * @date 2023/4/26 15:17
 * @description account业务接口
 */
public interface AccountService {

    /**
     * 用户登录
     */
    Result findAccountByLogin(AccountDto accountDto);

    /**
     * 分页查询账户信息
     */
    Result findAccountByPage(BaseDto baseDto);

    /**
     * 根据id删除账户【单个删除】
     */
    Result removeAccountById(Long id);

    /**
     * 重置密码
     */
    Result modifyResetPwdById(Long id);

    /**
     *保存账户
     */
    Result saveAccount(String username);

}
