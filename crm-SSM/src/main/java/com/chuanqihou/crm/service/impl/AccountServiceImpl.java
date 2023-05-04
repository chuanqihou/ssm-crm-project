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
            //判断账户状态
            if (account.getState() != 1) {
                return new Result(-1, "账号已封禁，如需帮助，请联系客服！");
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
        HttpSession session = WebScopeUtil.getHttpSession();
        //验证用户角色
        Account account = (Account) session.getAttribute(Constants.ACCOUNT_SESSION_KEY);
        if (account == null || account.getRole() != 1) {
            return new Result(-1, "账户权限不够");
        }
        //执行重置
        int updateAccountPwdById = accountMapper.updateAccountPwdById(id);
        if (updateAccountPwdById == 0) {
            return new Result(-1, "重置密码失败");
        }
        //若重置账户为当前管理员账户则退出登录【重置session】
        if (id == account.getId()) {
            session.removeAttribute(Constants.ACCOUNT_SESSION_KEY);
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

    /**
     * 更改账户状态
     * @param id
     * @return
     */
    @Override
    public Result modifyStateById(Long id) {
        //数据效验
        if (id ==null || id <= 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //验证用户角色
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);
        if (account == null || account.getRole() != 1) {
            return new Result(-1, "账户权限不够");
        }
        //查询账户信息
        Account acc = accountMapper.selectAccountById(id);
        //判断操作账户
        if (acc.getRole() == 1) {
            return new Result(-1, "不能禁用管理员");
        }
        //修改账号状态
        int newState = 0;
        if (acc.getState() == 0) {
            newState = 1;
        }
        int updateAccountState = accountMapper.updateAccountState(id, newState);
        if (updateAccountState != 1) {
            return new Result(-1, "更改账号状态失败");
        }
        return new Result();
    }

    /**
     * 修改账户头像
     * @param imgUrl 头像文件名/地址
     * @return result
     */
    @Override
    public Result modifyHeadImgById(String imgUrl) {
        if (imgUrl == null) {
            return Result.DATE_FORMAT_ERROR;
        }
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);

        int i = accountMapper.updateAccountHeadImg(account.getId(), imgUrl);
        if (i == 0) {
            return new Result(-1,"修改失败");
        }
        return new Result(200,"success",imgUrl);
    }

    /**
     * 修改用户密码
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return result
     */
    @Override
    public Result modifyAccountPasswordById(String oldPwd, String newPwd) {
        //密码正则，6-12位，包含数字大写字母，小写字母和特殊符号
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).{6,12}$";
        //数据效验
        if (oldPwd == null || !oldPwd.matches(regex) || newPwd == null || !newPwd.matches(regex)) {
            return Result.DATE_FORMAT_ERROR;
        }
        //加密
        oldPwd = MD5Util.finalMd5(oldPwd);
        newPwd = MD5Util.finalMd5(newPwd);
        //获取当前登录用户信息
        Account account = (Account) WebScopeUtil.getHttpSession().getAttribute(Constants.ACCOUNT_SESSION_KEY);
        //比对原密码
        //Account ac = accountMapper.selectAccountById(account.getId());
        if (!Objects.equals(account.getPwd(), oldPwd)) {
            return new Result(-1, "修改失败，原密码错误");
        }
        //更新密码
        int i = accountMapper.updateAccountPasswordById(account.getId(), newPwd);
        if (i == 0) {
            return new Result(-1, "修改失败");
        }
        //返回结果
        return new Result();
    }
}
