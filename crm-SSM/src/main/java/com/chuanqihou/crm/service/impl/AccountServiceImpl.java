package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Constants;
import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Account;
import com.chuanqihou.crm.dto.AccountDto;
import com.chuanqihou.crm.dto.BaseDto;
import com.chuanqihou.crm.mapper.AccountMapper;
import com.chuanqihou.crm.service.AccountService;
import com.chuanqihou.crm.util.MD5Util;
import com.chuanqihou.crm.util.WebScopeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author 传奇后
 * @date 2023/4/26 16:35
 * @description 账户业务层
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 用户登录
     * @param accountDto 参数
     * @return  result
     */
    @Override
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
            //保存用户信息
            session.setAttribute(Constants.ACCOUNT_SESSION_KEY,account);
            //返回数据
            return new Result(200,"success",account);
        } finally {
            //销毁验证码
            session.removeAttribute(Constants.CODE_SESSION_KEY);
        }
    }

    /**
     * 分页查询
     * @param baseDto   参数
     * @return  result
     */
    @Override
    public Result findAccountByPage(BaseDto baseDto) {
        //开启分页
        Page<Account> objects = PageHelper.startPage(baseDto.getPageNum(), baseDto.getPageSize());
        //查询数据
        accountMapper.selectAccountByPage();
        //获取pageInfo
        PageInfo<Account> accountPageInfo = objects.toPageInfo();
        //返回数据
        return new Result(200,"success",accountPageInfo.getList(),accountPageInfo.getTotal());
    }

    /**
     * 根据id删除账户【单个删除】
     * @param id 账户Id
     * @return  result
     */
    @Override
    public Result removeAccountById(Long id) {
        //数据效验
        if (id ==null || id <= 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //验证用户角色
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);
        if (account == null || account.getRole() != 1) {
            return new Result(-1, "账户权限不够");
        }
        //验证当前删除用户id
        if (Objects.equals(account.getId(), id)) {
            return new Result(-1, "不能删除当前登录账户");
        }
        //执行删除
        int deleteAccountById = accountMapper.deleteAccountById(id);
        if (deleteAccountById == 0) {
            return new Result(-1, "账户删除失败");
        }
        //返回数据
        return new Result();
    }

    /**
     * 重置账户密码【默认密码为123456aA.】
     * @param id 账户id
     * @return result
     */
    @Override
    public Result modifyResetPwdById(Long id) {
        //数据效验
        if (id ==null || id <= 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //验证用户角色
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);
        if (account == null || account.getRole() != 1) {
            return new Result(-1, "账户权限不够");
        }
        //执行重置
        int updateAccountPwdById = accountMapper.updateAccountPwdById(id);
        if (updateAccountPwdById == 0) {
            return new Result(-1, "重置密码失败");
        }
        //返回数据
        return new Result();
    }

    /**
     * 保存账户信息
     * @param username 账户名
     * @return result
     */
    @Override
    public Result saveAccount(String username) {
        //数据效验
        if (username==null || !username.matches("\\w{4,20}")){
            return Result.DATE_FORMAT_ERROR;
        }
        //验证用户角色
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);
        if (account == null || account.getRole() != 1) {
            return new Result(-1, "账户权限不够");
        }
        //执行保存
        int insertAccount = accountMapper.insertAccount(username);
        if (insertAccount == 0) {
            return new Result(-1, "新增账户失败");
        }
        //返回数据
        return new Result();
    }
}
