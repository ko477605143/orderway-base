package com.orderway.system.bus.modular.auth.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.auth.handler.HandlerException;
import com.orderway.system.bus.modular.auth.handler.HandlerExceptionEnum;
import com.orderway.system.bus.modular.auth.handler.HandlerNewVService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "API_获取用户基本信息接口拓展", tags = "API_获取用户基本信息接口拓展")
@RestController
@RequestMapping("/handler/ext/new")
public class ExtHandlerNewController {

    @Resource
    private AuthService authService;

    @Resource
    private HandlerNewVService handlerNewVService;

    /**
     * 根据应用编码获取应用相关信息
     *
     * @param code 应用编码
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据应用编码 获取应用相关信息")
    @GetMapping("/getAppInfo")
    public ResponseData getAppInfo(String code) {
        return new SuccessResponseData(handlerNewVService.getAppInfo(code));
    }

    // 参数提示
    @ApiImplicitParam(name = "dict", value = "1.account，2.password，3.c_type（客户端类型 0:pc 1:app） 4.app_code ：应用编码")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "用户账号密码登入,需要传入应用code")
    @PostMapping("/login")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("account");
        String password = dict.getStr("password");
        String c_type = dict.getStr("c_type");
        String app_code = dict.getStr("app_code");

        //=====param check=========================
        if (ObjectUtil.hasEmpty(account, password, c_type, app_code)) {
            throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
        } else {
            if (c_type.equals("0")) {
            } else if (c_type.equals("1")) {
            } else if (c_type.equals("2")) {
            } else {
                throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
            }
        }

        String token = authService.login(account, password, c_type, app_code);
        return new SuccessResponseData(token);
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("根据token 获取用户的角色，应用，菜单信息")
    @PostMapping("/getUserInfoByToken")
    public ResponseData getUserInfoByToken() {
        return new SuccessResponseData(handlerNewVService.getUserInfoByToken());
    }
}
