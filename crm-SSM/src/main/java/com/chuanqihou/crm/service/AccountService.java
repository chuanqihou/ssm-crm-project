package com.chuanqihou.crm.service;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.dto.AccountDto;

/**
 * @author 传奇后
 * @date 2023/4/26 15:17
 * @description
 */
public interface AccountService {

    Result findAccountByLogin(AccountDto accountDto);

}
