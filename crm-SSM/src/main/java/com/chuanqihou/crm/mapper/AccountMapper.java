package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Account;
import com.chuanqihou.crm.dto.AccountDto;

/**
 * @author 传奇后
 * @date 2023/4/26 15:18
 * @description
 */
public interface AccountMapper {

    /**
     * 用户登录
     * @param accountDto
     * @return
     */
    Account selectAccountByLogin(AccountDto accountDto);

}
