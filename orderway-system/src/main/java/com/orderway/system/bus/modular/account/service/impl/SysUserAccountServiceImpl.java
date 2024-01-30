package com.orderway.system.bus.modular.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orderway.system.bus.modular.account.entity.SysUserAccount;
import com.orderway.system.bus.modular.account.mapper.SysUserAccountMapper;
import com.orderway.system.bus.modular.account.service.SysUserAccountService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public class SysUserAccountServiceImpl extends ServiceImpl<SysUserAccountMapper, SysUserAccount>
        implements SysUserAccountService {

    /**
     * 向用户账户表 添加关系
     *
     * @param accountId 账户Id
     * @param userId    用户Id
     */
    @Override
    public void add(Long accountId, Long userId) {

        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setAccountId(accountId);
        sysUserAccount.setUserId(userId);

        this.save(sysUserAccount);

    }

    /**
     * 删除用户账户表 关系信息
     *
     * @param accountId 子账户id
     * @param userId    用户id
     */
    @Override
    public void delete(Long accountId, Long userId) {
        LambdaQueryWrapper<SysUserAccount> qw = new LambdaQueryWrapper();
        qw.eq(SysUserAccount::getAccountId, accountId)
                .eq(SysUserAccount::getUserId, userId);
        this.remove(qw);
    }
}
