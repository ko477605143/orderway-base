/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.service;

import cn.hutool.core.lang.Dict;
import com.orderway.core.pojo.page.PageResult;
import com.orderway.system.bus.modular.app.entity.SysApp;
import com.orderway.system.bus.modular.app.param.SysAppParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orderway.system.bus.modular.dict.param.SysDictTypeParam;
import io.swagger.models.auth.In;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

/**
 * 系统应用service接口
 *
 * @author oderway
 * @date 2020/3/13 16:14
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 获取用户应用相关信息
     *
     * @param userId 用户id
     * @return 用户拥有的应用信息，格式：[{"code":"system","name":"系统应用","active":true}]
     * @author oderway
     * @date 2020/3/13 16:25
     */
    List<Dict> getLoginApps(Long userId);

    /**
     * 查询系统应用
     *
     * @param sysAppParam 查询参数
     * @return 查询分页结果
     * @author oderway
     * @date 2020/3/24 20:55
     */
    PageResult<SysApp> page(SysAppParam sysAppParam);

    /**
     * 添加系统应用
     *
     * @param sysAppParam 添加参数
     * @author oderway
     * @date 2020/3/25 14:57
     */
    void add(SysAppParam sysAppParam);

    /**
     * 删除系统应用
     *
     * @param sysAppParam 删除参数
     * @author oderway
     * @date 2020/3/25 14:57
     */
    void delete(SysAppParam sysAppParam);

    /**
     * 编辑系统应用
     *
     * @param sysAppParam 编辑参数
     * @author oderway
     * @date 2020/3/25 14:58
     */
    void edit(SysAppParam sysAppParam);

    /**
     * 查看系统应用
     *
     * @param sysAppParam 查看参数
     * @return 系统应用
     * @author oderway
     * @date 2020/3/26 9:50
     */
    SysApp detail(SysAppParam sysAppParam);

    /**
     * 系统应用列表
     *
     * @param sysAppParam 查询参数
     * @return 系统应用列表
     * @author oderway
     * @date 2020/4/19 14:56
     */
    List<SysApp> list(SysAppParam sysAppParam);

    /**
     * 设为默认应用
     *
     * @param sysAppParam 设为默认应用参数
     * @author oderway
     * @date 2020/6/29 16:49
     */
    void setAsDefault(SysAppParam sysAppParam);

    /**
     * 获取应用下拉框的值
     * @return 增强版hashMap，格式：[{"code:":"1", "value":"正常"}]
     */
    List<Map> appDropDown(SysDictTypeParam sysDictTypeParam);

    /**
     * 查询应用管理信息并分页
     */
    PageResult<Map> selectAppByPage(SysAppParam sysAppParam);

    List<Map<String, Object>> selectAll();

    /**
     * 根据角色 找到对应的所有应用
     * @param roleId 1个角色
     */
    SysApp getAllByRoleId(Long roleId);

    /**
     * 根据角色 找到对应的所有应用
     * @param roleIds 角色集合
     */
    List<SysApp> getAllByRoleIds(List<Long> roleIds);

    /**
     * 根据登入用户 获取所有应用信息
     *
     */
    List<Dict> getAllApp(List<Long> list);

    /**
     * 根据登入用户Id 获取所有的应用
     * @param userId
     * @return
     */
    List<Map> getAllAppByUserId(long userId);

    /**
     * 根据应用编码 获取应用信息
     */
    Map getAppInfo(String app);

    /**
     * 判断有没有该应用编码的权限
     */
    Integer checkCode(String code, Long userId);

    /**
     * 修改状态
     */
    void changeStatus(SysAppParam sysAppParam);
}
