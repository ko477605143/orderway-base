package com.orderway.system.bus.modular.auth.controller;

import cn.hutool.core.lang.Dict;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.system.bus.modular.auth.handler.HandlerException;
import com.orderway.system.bus.modular.auth.handler.HandlerExceptionEnum;
import com.orderway.system.bus.modular.auth.handler.HandlerNewVService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@ApiSort(value = 1)
@Api(value = "API_获取用户基本信息接口", tags = "API_获取用户基本信息接口")
@RestController
@RequestMapping("/handler/new")
public class HandlerNewController {

    @Resource
    private HandlerNewVService handlerNewVService;

    @Resource
    private AuthService authService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据Token获取已登录用户所有的角色")
    @PostMapping("/getLoginUserRoles")
    public ResponseData getSysLoginUserRoles(HttpServletRequest httpServletRequest) {
        return new SuccessResponseData(handlerNewVService.getSysLoginUserRoles());
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "根据Token获取已登录用户所有的应用")
    @PostMapping("/getLoginUserApps")
    public ResponseData getLoginUserApps(HttpServletRequest request) {
        List<Map> sysLoginUserApps = handlerNewVService.getSysLoginUserApps();
        return new SuccessResponseData(sysLoginUserApps);
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "根据Token获取已登录用户对应的一级菜单")
    @PostMapping("/getLoginUserMenus")
    public ResponseData getLoginUserMenus() {
        return new SuccessResponseData(handlerNewVService.getMenusByUserId());
    }

    // 参数提示
    @ApiImplicitParam(name = "dict", value = "menu_id (菜单id)")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "根据菜单Id获取对应子菜单")
    @PostMapping("/getMenusById")
    public ResponseData getMenusById(@RequestBody Dict dict) {
        Long menuId = dict.getLong("menuId");
        return new SuccessResponseData(handlerNewVService.getMenusByPid(menuId));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "根据Token获取用户信息")
    @PostMapping("/getUserByToken")
    public ResponseData getUserByToken() {
        return new SuccessResponseData(handlerNewVService.getLoginUser());
    }

    /**
     * @param dict {"account":"","password":"","":"","":"","":""}
     * @return
     */
    // 参数提示
    @ApiImplicitParam(name = "dict", value = "account，password，c_type（客户端类型）0：pc端 1：app")
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

    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "刷新token时长")
    @PostMapping("/flushToken")
    public ResponseData flushToken() {
        authService.flushToken();
        return new SuccessResponseData();
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "判断token是否有效")
    @PostMapping("/checkToken")
    public ResponseData checkToken() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        String token = authService.getTokenFromRequest(request);
        authService.checkToken(token);
        return new SuccessResponseData();
    }

    /**
     * 退出登录
     *
     * @author oderway
     * @date 2020/3/16 15:02
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "退出登录(header传入token)")
    @GetMapping("/logout")
    public ResponseData logout() {
        authService.logout();
        return new SuccessResponseData();
    }

    /**
     * 根据token获取所有菜单
     *
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "根据token获取用户所有菜单")
    @GetMapping("/getAllMenuByToekn")
    public ResponseData getAllMenuByToekn(@RequestParam(required = false) Integer type) {
        return new SuccessResponseData(handlerNewVService.getAllMenuByToken(type));
    }

    ///**
    // * 根据token,获取所有角色下的用户
    // */
    //@ApiOperationSupport(order = 10)
    //@ApiOperation(value = "根据token,获取所有角色下的用户")
    //@GetMapping("/getAllUserByToekn")
    //public ResponseData getAllUserByToekn(@RequestParam(required = false) String roleCode) {
    //    return new SuccessResponseData(handlerNewVService.getAllUserByToekn(roleCode));
    //}

    /**
     * 根据token,获取所有角色下的用户
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "获取平台所有角色")
    @PostMapping("/getAllRole")
    public ResponseData getAllRole() {
        return new SuccessResponseData(handlerNewVService.getAllRole());
    }

}
