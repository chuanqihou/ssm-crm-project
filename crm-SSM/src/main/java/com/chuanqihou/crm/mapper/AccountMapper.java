package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Account;
import com.chuanqihou.crm.dto.AccountDto;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/26 15:18
 * @description
 */
public interface AccountMapper {

    /**
     * 用户登录
     * @param accountDto 参数
     * @return  account
     */
    Account selectAccountByLogin(AccountDto accountDto);

    /**
     * 分页查询
     * @return  account集合
     */
    List<Account> selectAccountByPage();

    /**
     * 根据id删除账户【单个删除】
     */
    int deleteAccountById(Long id);


    /**
     * 重置密码
     */
    int updateAccountPwdById(Long id);

    /**
     *新增账户信息
     */
    int insertAccount(String username);

}
