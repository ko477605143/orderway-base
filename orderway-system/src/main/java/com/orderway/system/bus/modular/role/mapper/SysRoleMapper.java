/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.role.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderway.system.bus.modular.role.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderway.system.bus.modular.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统角色mapper接口
 *
 * @author oderway
 * @date 2020/3/13 15:51
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 查询系统角色
     *
     * @param page 分页插件
     * @return 查询分页结果
     */
    Page<Map> getPage(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);

    /**
     * 根据用户Id获取对应的所有角色
     *
     * @param userId 用户Id
     */
    List<Map<String, Object>> getAllByUserId(@Param("id") Long userId);

    /**
     * 根据应用编码 获取角色结果集
     *
     * @param code 应用编码
     */
    List<Map> getRolesByCode(@Param("code") String code, @Param("userId") Long userId);

    List<Map> getUserByToken(@Param("userId") Long userId, @Param("roleCode") String roleCode);

    List<Map> getAllRole();
}
