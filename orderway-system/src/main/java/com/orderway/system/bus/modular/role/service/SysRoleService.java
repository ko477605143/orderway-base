/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.role.service;

import cn.hutool.core.lang.Dict;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.page.PageResult;
import com.orderway.system.bus.modular.app.entity.SysApp;
import com.orderway.system.bus.modular.role.entity.SysRole;
import com.orderway.system.bus.modular.role.param.SysRoleParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orderway.system.bus.modular.user.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 系统角色service接口
 *
 * @author oderway
 * @date 2020/3/13 15:47
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取用户角色相关信息
     *
     * @param userId 用户id
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author oderway
     * @date 2020/3/13 16:26
     */
    List<Dict> getLoginRoles(Long userId);

    /**
     * 查询系统角色
     *
     * @param sysRoleParam 查询参数
     * @return 查询分页结果
     * @author oderway
     * @date 2020/3/28 14:53
     */
    PageResult<SysRole> page(SysRoleParam sysRoleParam);

    /**
     * 根据角色名模糊搜索系统角色列表
     *
     * @param sysRoleParam 查询参数
     * @return 增强版hashMap，格式：[{"id":456, "name":"总经理(zjl)"}]
     * @author oderway
     * @date 2020/4/14 9:21
     */
    List<Dict> list(SysRoleParam sysRoleParam);

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author oderway
     * @date 2020/4/5 16:47
     */
    List<Dict> dropDown();

    /**
     * 添加系统角色
     *
     * @param sysRoleParam 添加参数
     * @author oderway
     * @date 2020/3/28 14:54
     */
    void add(SysRoleParam sysRoleParam);

    /**
     * 删除系统角色
     *
     * @param sysRoleParam 删除参数
     * @author oderway
     * @date 2020/3/28 14:54
     */
    void delete(SysRoleParam sysRoleParam);

    /**
     * 编辑系统角色
     *
     * @param sysRoleParam 编辑参数
     * @author oderway
     * @date 2020/3/28 14:54
     */
    void edit(SysRoleParam sysRoleParam);

    /**
     * 查看系统角色
     *
     * @param sysRoleParam 查看参数
     * @return 系统角色
     * @author oderway
     * @date 2020/3/26 9:50
     */
    SysRole detail(SysRoleParam sysRoleParam);

    /**
     * 授权菜单
     *
     * @param sysRoleParam 授权参数
     * @author oderway
     * @date 2020/3/28 16:19
     */
    void grantMenu(SysRoleParam sysRoleParam);

    /**
     * 授权数据
     *
     * @param sysRoleParam 授权参数
     * @author oderway
     * @date 2020/3/28 16:20
     */
    void grantData(SysRoleParam sysRoleParam);

    /**
     * 根据角色id集合获取数据范围id集合
     *
     * @param roleIdList 角色id集合
     * @param orgId      机构id
     * @return 数据范围id集合
     * @author oderway
     * @date 2020/4/5 17:41
     */
    List<Long> getUserDataScopeIdList(List<Long> roleIdList, Long orgId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     * @return 角色名称
     * @author oderway
     * @date 2020/5/22 16:15
     */
    String getNameByRoleId(Long roleId);

    /**
     * 查询角色拥有菜单
     *
     * @param sysRoleParam 查询参数
     * @return 菜单id集合
     * @author oderway
     * @date 2020/5/29 14:03
     */
    List<Long> ownMenu(SysRoleParam sysRoleParam);

    /**
     * 查询角色拥有数据
     *
     * @param sysRoleParam 查询参数
     * @return 数据范围id集合
     * @author oderway
     * @date 2020/5/29 14:04
     */
    List<Long> ownData(SysRoleParam sysRoleParam);

    /**
     * 查询所有状态为正常的应用
     */
    List<SysApp> getAllApp();

    /**
     * 查询系统角色 分页
     *
     * @param sysRoleParam 查询参数
     * @return 查询分页结果
     */
    PageResult<Map> getPage(SysRoleParam sysRoleParam);

    /**
     * 根据用户Id 获取所有角色信息
     */
    List<Map<String,Object>> getAllRoleByUserId(Long userId);

    /**
     * 根据登入用户，获取所有的角色
     */
    List<Dict> getAllRole(SysLoginUser sysLoginUser);

    /**
     * 根据应用编码 获取角色结果集
     *
     * @param code 应用编码
     */
    List<Map> getRolesByCode(String code, Long userId);

    List<Map> getUserByToken(Long userId, String roleCode);

    List<Map> getAllRole();
}
