/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.auth.controller;

import cn.hutool.core.lang.Dict;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.context.login.LoginContextHolder;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.auth.bus.modular.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 *
 * @author oderway
 * @date 2020/3/11 12:20
 */
//@Api(value = "测试")
@RestController
public class SysLoginController {

    @Resource
    private AuthService authService;

    /**
     * 账号密码登录
     *
     * @author oderway
     * @date 2020/3/11 15:52
     */
    //@ApiOperation(value = "登录")
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
   // @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/getLoginUser")
    public ResponseData getLoginUser() {
        return new SuccessResponseData(LoginContextHolder.me().getSysLoginUserUpToDate());
    }

}
