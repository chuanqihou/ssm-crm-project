package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Constants;
import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Account;
import com.chuanqihou.crm.dto.AccountDto;
import com.chuanqihou.crm.mapper.AccountMapper;
import com.chuanqihou.crm.service.AccountService;
import com.chuanqihou.crm.util.MD5Util;
import com.chuanqihou.crm.util.WebScopeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/4/26 16:35
 * @description 账户业务层
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 用户登录
     * @param accountDto
     * @return
     */
    @Override
    @Transactional
    public Result findAccountByLogin(AccountDto accountDto) {
        HttpSession session = WebScopeUtil.getHttpSession();
        try {
            //效验验证码
            String systemCaptchaCode = (String) session.getAttribute(Constants.CODE_SESSION_KEY);
            if (!Objects.equals(systemCaptchaCode, accountDto.getCaptcha())) {
                return new Result(-1, "验证码错误");
            }
            //处理密码
            String newPwd = MD5Util.finalMd5(accountDto.getPwd());
            accountDto.setPwd(newPwd);
            //验证账户名和密码
            Account account = accountMapper.selectAccountByLogin(accountDto);
            if (account == null) {
                return new Result(-1, "账户名或密码错误,请重新输入");
            }
            //返回数据
            return new Result(200,"success",account);
        } finally {
            //销毁验证码
            session.removeAttribute(Constants.CODE_SESSION_KEY);
        }
    }
}
