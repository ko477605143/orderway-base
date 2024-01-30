/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orderway.system.bus.modular.app.entity.SysApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统应用mapper接口
 *
 * @author oderway
 * @date 2020/3/13 16:17
 */
public interface SysAppMapper extends BaseMapper<SysApp> {

    /**
     * 通过字典类型Id获取字典值
     *
     * @param dictTypeId 字典类型Id
     * @return 字典值列表
     */
    List<Map> getDictCodeAndValueByDictTypeId(Long dictTypeId);

    /**
     * 查询应用管理信息并分页
     *
     * @param page 分页插件
     */

    Page<Map> page(@Param("page") Page page, @Param("ew") QueryWrapper ew);

    /**
     * 根据角色 找到对应的所有应用
     *
     * @param roleId 1个角色
     */
    SysApp getAllByRoleId(@Param("id") long roleId);

    /**
     * 根据角色 找到对应的所有应用
     *
     * @param roleIds 多个角色
     */
    List<SysApp> getAllByRoleIds(@Param("list") List<Long> roleIds);

    /**
     * 根据登入角色Id找到对应的应用
     */
    List<Map> getAllAppByUserId(@Param("id") long userId);

    /**
     * 判断有没有该应用编码的权限
     * @param code 应用编码
     * @param userId 用户id
     */
    Integer checkCode(@Param("code") String code, @Param("userId") Long userId);

    List<Map<String,Object>> get_all_app();
}
