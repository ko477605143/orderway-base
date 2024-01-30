/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.menu.service;

import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.node.LoginMenuTreeNode;
import com.orderway.system.bus.modular.menu.entity.SysMenu;
import com.orderway.system.bus.modular.menu.node.MenuBaseTreeNode;
import com.orderway.system.bus.modular.menu.param.SysMenuParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单service接口
 *
 * @author oderway
 * @date 2020/3/13 16:05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限相关信息
     *
     * @param userId 用户id
     * @return 权限集合
     * @author oderway
     * @date 2020/3/13 16:26
     */
    List<String> getLoginPermissions(Long userId);

    /**
     * 获取用户AntDesign菜单相关信息，前端使用
     *
     * @param userId  用户id
     * @param appCode 应用编码
     * @return AntDesign菜单信息结果集
     * @author yubaoshan
     * @date 2020/4/17 17:48
     */
    List<LoginMenuTreeNode> getLoginMenusAntDesign(Long userId, String appCode);

    /**
     * 获取用户菜单所属的应用编码集合
     *
     * @param userId 用户id
     * @return 用户菜单所属的应用编码集合
     * @author oderway
     * @date 2020/3/21 11:01
     */
    List<String> getUserMenuAppCodeList(Long userId);

    /**
     * 系统菜单列表（树表）
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树表列表
     * @author oderway
     * @date 2020/3/26 10:19
     */
    List<SysMenu> list(SysMenuParam sysMenuParam);

    /**
     * 添加系统菜单
     *
     * @param sysMenuParam 添加参数
     * @author oderway
     * @date 2020/3/27 9:03
     */
    void add(SysMenuParam sysMenuParam);

    /**
     * 删除系统菜单
     *
     * @param sysMenuParam 删除参数
     * @author oderway
     * @date 2020/3/27 9:03
     */
    void delete(SysMenuParam sysMenuParam);

    /**
     * 编辑系统菜单
     *
     * @param sysMenuParam 编辑参数
     * @author oderway
     * @date 2020/3/27 9:03
     */
    void edit(SysMenuParam sysMenuParam);

    /**
     * 查看系统菜单
     *
     * @param sysMenuParam 查看参数
     * @return 系统菜单
     * @author oderway
     * @date 2020/3/27 9:03
     */
    SysMenu detail(SysMenuParam sysMenuParam);

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author oderway
     * @date 2020/3/27 15:56
     */
    List<MenuBaseTreeNode> tree(SysMenuParam sysMenuParam);

    /**
     * 获取菜单树,用于角色中根据应用授权菜单
     */
    List<MenuBaseTreeNode> treeByApplication(String sysMenuParam);

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author oderway
     * @date 2020/4/5 15:01
     */
    List<MenuBaseTreeNode> treeForGrant(SysMenuParam sysMenuParam);

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author oderway
     * @date 2020/6/28 12:14
     */
    boolean hasMenu(String appCode);

    /**
     * 根据应用的编码 获取对应的菜单
     *
     * @param appCode 应用编码
     * @return 菜单信息结果集
     */
    List<LoginMenuTreeNode> getAllMenuByApp(Long userId, String appCode);

    /**
     * 根据用户Id 获取pid = 0 的菜单
     */
    List<Map> getOneLevelMenusByUserId(Long userId);

    /**
     * 根据菜单id 获取子菜单（pid）
     */
    List<SysMenu> getMenusByPid(Long menuId);

    /**
     * 根据应用编号，角色id 获取用户对应的菜单
     *
     * @param userId   用户id
     * @param menuCode 菜单编码
     */
    List<LoginMenuTreeNode> getUserMenu(Long userId , String menuCode);

    /**
     * 根据用户id 获取所有菜单
     * @param userId
     * @return
     */
    List<Map> getAllMenu(Long userId, Integer type);
}
