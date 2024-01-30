/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.menu.mapper;

import com.orderway.core.validation.date.DateValue;
import com.orderway.system.bus.modular.menu.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单mapper接口
 *
 * @author oderway
 * @date 2020/3/13 16:05
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户Id 获取pid = 0 的菜单 （根据角色id 和 角色code过滤）
     *
     * @param status 状态
     * @param type   类型
     */
    List<Map> getOneLevelMenusByUserId(@Param("userId") Long userId, @Param("status") Integer status, @Param("type") Integer type);

    /**
     * 根据应用编号，角色id 获取用户对应的菜单
     *
     * @param userId    角色结果集
     * @param menuCode 菜单编码
     * @param status   状态
     * @param type     类型
     */
    List<SysMenu> getUserMenu(@Param("userId") Long userId, @Param("code") String menuCode
            , @Param("status") Integer status, @Param("type") Integer type);


    /**
     * 根据pid 获取对应子菜单 根据菜单权限过滤
     *
     * @param pid    菜单id
     * @param userId 用户id
     * @param status 状态
     * @param type   类型
     */
    List<Map> getMenuByPid(@Param("pid") Integer pid, @Param("userId") Long userId,
                           @Param("status") Integer status, @Param("type") Integer type);

    List<Map> getAllMenu(@Param("userId") Long userId, @Param("type") Integer type);

}
