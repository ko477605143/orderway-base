package com.orderway.system.bus.modular.auth.controller;

import cn.hutool.core.lang.Dict;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.auth.handler.HandlerException;
import com.orderway.system.bus.modular.auth.handler.HandlerExceptionEnum;
import com.orderway.system.bus.modular.auth.handler.HandlerSystemServices;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统控制器
 *
 * @author oderway
 * @date 2020/3/11 12:20
 */
@ApiSort(value = 1)
@Api(value = "系统基础框架接口", tags = "系统基础框架接口")
@RestController
@RequestMapping("/handler")
public class HandlerController {
    @Resource
    private AuthService authService;

    @Autowired
    private HandlerSystemServices handlerSystemServices;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "账号密码登录")
    @PostMapping("/login")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("account");
        String password = dict.getStr("password");
        String c_type = dict.getStr("c_type");

        //=====param check=========================
        if (account == null || account.equals("") || password == null || password.equals("") || c_type == null || c_type.equals("")) {
            throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
        } else {
            if (c_type.equals("0")) {
            } else if (c_type.equals("1")) {
            } else if (c_type.equals("2")) {
            } else {
                throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
            }
        }

        String token = authService.login(account, password, c_type,null);
        return new SuccessResponseData(token);


    }

    /**
     * 退出登录
     *
     * @author oderway
     * @date 2020/3/16 15:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }


    /**
     * 获取当前登录用户信息
     *
     * @author oderway, fengshuonan
     * @date 2020/3/23 17:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "获取当前登录用户信息")
    @PostMapping(value = "/getLoginUser")
    public ResponseData getLoginUser(HttpServletRequest request) {
        // return new SuccessResponseData(LoginContextHolder.me().getSysLoginUserUpToDate());
        return new SuccessResponseData(handlerSystemServices.getSysLoginUser(request));
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "获取登录用户所有的应用")
    @PostMapping("/getLoginUserApps")
    public ResponseData getLoginUserApps(HttpServletRequest request) {
        return new SuccessResponseData(handlerSystemServices.getSysLoginUserApps(request));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "获取登录用户所有的角色")
    @PostMapping("/getLoginUserRoles")
    public ResponseData getSysLoginUserRoles(HttpServletRequest request) {
        return new SuccessResponseData(handlerSystemServices.getSysLoginUserRoles(request));
    }

    @ApiOperationSupport(order = 6)
     @ApiOperation(value = "获取登录用户所有的菜单")
    @PostMapping("/getLoginUserMenus")
    public ResponseData getLoginUserMenus(HttpServletRequest request) {
        return new SuccessResponseData(handlerSystemServices.getSysLoginUserMenus(request));
    }

    @ApiOperationSupport(order = 7)
     @ApiOperation(value = "获取客户端登录用户所有的菜单")
    @PostMapping("/getLoginUserMenusByClient")
    public ResponseData getLoginUserMenusByClient(HttpServletRequest request) {
        return new SuccessResponseData(handlerSystemServices.selMenusByUserIdAndClientType(request));
    }


    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "获取客户端登录用户所有应用")
    @PostMapping("/getLoginUserAppsByClient")
    public ResponseData getLoginUserAppsByClient(HttpServletRequest request) {
        return new SuccessResponseData(handlerSystemServices.selAppByUserIdAndClientType(request));
    }




}
