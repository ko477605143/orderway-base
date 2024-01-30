package com.orderway.system.bus.modular.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orderway.system.bus.modular.account.entity.SysAccount;
import com.orderway.system.bus.modular.account.param.SysAccountParam;
import com.orderway.system.bus.modular.user.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysAccountService extends IService<SysAccount> {

    /**
     * 为用户新增账号
     */
    void add(SysAccountParam sysAccountParam);

    /**
     * 根据Token
     * 查询用户对应多账号信息
     */
    List<Map> listByToken();

    /**
     * 根据用户id 需要传入用户id
     *
     * @param userId 用户ID
     *               查询用户对应子账号信息
     */
    List<Map> listById(Long userId);

    /**
     * 根据子账户Id 修改信息
     */
    void edit(SysAccountParam sysAccountParam);

    /**
     * 根据子账户id 获取子账户信息
     */
    SysAccount getAccount(Long accountId);

    /**
     * 根据子账号id修改密码
     */
    void updatePwd(SysAccountParam sysAccountParam);

    /**
     * 根据子账户Id 重置密码
     */
    void resetPwd(Long accountId);

    /**
     * 删除子账户信息
     */
    void delete(SysAccountParam sysAccountParam);

    /**
     * 子账户账号，查询子账号信息
     */
    SysAccount getAccountByAcc(String account);

    /**
     * 根据子账户id ，找到用户
     */
    SysUser getUserByAccountId(Long accountId);

    /**
     * 校验参数，检查是否存在相同的账号（账户表）
     *
     * @param account       账户信息
     * @param isExcludeSelf 为false 不排除自己
     */
    void checkParam(String account, Long id, boolean isExcludeSelf);

}
