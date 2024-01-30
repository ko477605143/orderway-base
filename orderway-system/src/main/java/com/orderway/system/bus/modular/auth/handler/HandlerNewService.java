package com.orderway.system.bus.modular.auth.handler;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.orderway.core.consts.CommonConstant;
import com.orderway.core.enums.CommonStatusEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.node.LoginMenuTreeNode;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import com.orderway.system.bus.modular.role.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandlerNewService {

    @Resource
    private HandlerSystemServices handlerSystemServices;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysAppService sysAppService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 根据用户信息 获取对应的角色信息
     *
     * @param
     * @return List<Dict> 角色结果集
     */
    public List<Dict> getSysLoginUserRoles() {
        SysLoginUser loginUser = this.getLoginUser();
        // 根据用户信息 获取对应的所有角色信息
        return sysRoleService.getAllRole(loginUser);
    }

    /**
     * 根据角色结果集 获取对应的应用信息
     *
     * @return List<Dict> 应用结果集
     */
    public List<Dict> getSysLoginUserApps() {
        // 获取当前登入用户对应的角色结果集
        List<Dict> dict = this.getSysLoginUserRoles();
        if (ObjectUtil.isEmpty(dict)) {
            // 如果当前用户没有对应角色 返回空结果
            return CollectionUtil.newArrayList();
        }
        // 将Dict结果集 转换为 roleid的 list集合
        List<Long> collect = dict.stream().map(i -> i.getLong(CommonConstant.ID)).collect(Collectors.toList());
        // 根据roleId 集合 获取对应的应用信息
        return sysAppService.getAllApp(collect);
    }

    /**
     * 获取当前登入用户信息
     *
     * @return
     */
    private SysLoginUser getLoginUser() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        // 从request 获取用户信息
        return handlerSystemServices.getSysLoginUser(request);
    }

}
