package com.orderway.system.bus.modular.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderway.system.bus.modular.account.entity.SysAccount;
import com.orderway.system.bus.modular.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysAccountMapper extends BaseMapper<SysAccount> {

    /**
     * 查询用户对应的 子账户
     *
     * @param userId 用户id
     * @return 账户结果集
     */
    List<Map> list(@Param("id") Long userId);

    /**
     * 查询子账号id 查询用户
     *
     * @param account 子账号id
     * @return 账户结果集
     */
    SysUser UserByAccountId(@Param("id") Long account);
}
