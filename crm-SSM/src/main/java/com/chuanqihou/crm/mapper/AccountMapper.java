package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Account;
import com.chuanqihou.crm.dto.AccountDto;
import org.apache.ibatis.annotations.Param;

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

    /**
     *根据账户id查询账户信息
     */
    Account selectAccountById(Long id);

    /**
     *根据账户Id修改账户状态
     */
    int updateAccountState(@Param("id") Long id, @Param("state") Integer state);

    /**
     *根据账户id修改头像
     */
    int updateAccountHeadImg(@Param("id") Long id,@Param("imgUrl") String imgUrl);

    /**
     *根据用户id修改用户密码
     */
    int updateAccountPasswordById(@Param("id") Long id, @Param("pwd") String pwd);
}
