/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.auth.controller;

import cn.hutool.core.lang.Dict;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.context.login.LoginContextHolder;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录控制器
 *
 * @author oderway
 * @date 2020/3/11 12:20
 */
@Api(value = "管理平台基础操作类",tags = "管理平台基础操作类")
@RestController
public class SysLoginController {

    @Resource
    private AuthService authService;

    /**
     * 账号密码登录
     *
     * @param dict{"account":"","password":""}
     * @return
     */
    @ApiOperation(value = "账号密码登录")
    @PostMapping("/login")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("account");
        String password = dict.getStr("password");
        String tenantCode = dict.getStr("tenantCode");

        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (ConstantContextHolder.getTenantOpenFlag()) {
            authService.cacheTenantInfo(tenantCode);
        }

        String token = authService.login(account, password);
        return new SuccessResponseData(token);
    }


    /**
     * 退出登录
     *
     * @author oderway
     * @date 2020/3/16 15:02
     */
    @ApiOperation(value = "退出登录(header传入token)")
    @GetMapping("/logout")
    public void logout() {
        authService.logout();
    }

    /**
     * 获取当前登录用户信息
     *
     * @author oderway, fengshuonan
     * @date 2020/3/23 17:57
     */
    @ApiOperation(value = "获取当前登录用户信息(header传入token)")
    @GetMapping("/getLoginUser")
    public ResponseData getLoginUser() {
        return new SuccessResponseData(LoginContextHolder.me().getSysLoginUserUpToDate());
    }

    /**
     * 获取当前登录用户的角色id集合
     *
     * @author oderway
     * @date 2020/3/23 8:58
     */
    @ApiOperation(value = "获取当前登录用户的角色id集合")
    @GetMapping("/getLoginUserRoleIds")
    private ResponseData getLoginUserRoleIds() {
        return new SuccessResponseData(LoginContextHolder.me().getLoginUserRoleIds());
    }

    /**
     * 当前登录用户的数据范围（组织机构id集合）
     */
    @ApiOperation(value = "当前登录用户的数据范围（组织机构id集合）")
    @GetMapping("/getLoginUserDataScopeIdList")
    public ResponseData getLoginUserDataScopeIdList() {
        return null;
    }

    /**
     * 管理员类型（0超级管理员 1非管理员）
     * 判断当前登录用户是否是超级管理员
     */
    @ApiOperation(value = "判断当前登录用户是否是超级管理员管理员类型（0超级管理员 1非管理员）")
    @GetMapping("/isSuperAdmin")
    public ResponseData isSuperAdmin() {
        return null;
    }

    /**
     * 判断当前登录用户是否包含某个角色
     *
     * @return
     */
    @ApiOperation(value = "判断当前登录用户是否包含某个角色")
    @GetMapping("/hasRole")
    public ResponseData hasRole() {
        return null;
    }

    /**
     * 判断当前登录用户是否有某资源的访问权限
     *
     * @return
     */
    @ApiOperation(value = "判断当前登录用户是否有某资源的访问权限")
    @GetMapping("/hasPermission")
    public ResponseData hasPermission() {
        return null;
    }

    /**
     * 获取当前登录的用户账号
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录的用户账号")
    @GetMapping("/getSysLoginUserAccount")
    public ResponseData getSysLoginUserAccount() {
        return null;
    }

    /**
     * 获取当前登录用户的id
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的id")
    @GetMapping("/getSysLoginUserId")
    public ResponseData getSysLoginUserId() {
        return null;
    }


    /**
     * 刷新token
     *
     * @return
     */
    @ApiOperation(value = "刷新token")
    @GetMapping("/flashToken")
    public ResponseData flashToken() {
        //token还活着刷新 已失效返回已失效
        return null;
    }

    /**
     * 刷新token时长（）
     *
     * @return
     */
    @ApiOperation(value = "刷新token时长")
    @GetMapping("/flashTokenByTime")
    public ResponseData flashTokenByTime(Long time) {
        //token还活着刷新 已失效返回已失效
        return null;
    }

}
