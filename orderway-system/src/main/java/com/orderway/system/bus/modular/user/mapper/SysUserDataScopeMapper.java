/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.user.mapper;

import com.orderway.system.bus.modular.user.entity.SysUserDataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户数据范围mapper接口
 *
 * @author oderway
 * @date 2020/3/13 15:46
 */
public interface SysUserDataScopeMapper extends BaseMapper<SysUserDataScope> {

    /**
     * 关联用户数据范围
     */
    void add_user_data(@Param("id") Long id, @Param("orgId") Long orgId, @Param("userId") Long userId);
}
