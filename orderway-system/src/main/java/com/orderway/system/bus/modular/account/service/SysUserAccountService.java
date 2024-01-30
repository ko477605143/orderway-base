package com.orderway.system.bus.modular.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orderway.system.bus.modular.account.entity.SysUserAccount;

public interface SysUserAccountService extends IService<SysUserAccount> {

    /**
     * 新增用户和子账户关系表
     *
     * @param accountId 子账户id
     * @param userId    用户id
     */
    void add(Long accountId, Long userId);

    /**
     * 删除用户和子账户关系表
     *
     * @param accountId 子账户id
     * @param userId    用户id
     */
    void delete(Long accountId, Long userId);
}
