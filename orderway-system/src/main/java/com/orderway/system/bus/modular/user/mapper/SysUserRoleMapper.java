/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.user.mapper;

import com.orderway.system.bus.modular.user.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户角色mapper接口
 *
 * @author oderway
 * @date 2020/3/13 15:46
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 关联用户角色信息
     * @param id 生成主鍵
     * @param roleId 角色id
     * @param userId  用户id
     */
    void add_user_role(@Param("id") Long id, @Param("userId") Long userId, @Param("roleId") Long roleId);
}
