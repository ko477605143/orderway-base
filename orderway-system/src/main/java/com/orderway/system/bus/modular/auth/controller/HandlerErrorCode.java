package com.orderway.system.bus.modular.auth.controller;

import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(value = 1)
@Api(value = "异常编码信息接口", tags = "异常编码信息接口")
@RestController
@RequestMapping("/handler/new")
public class HandlerErrorCode {

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "异常编码")
    @ApiResponses({
            @ApiResponse(code = 1011001001, message = "用户登录请求参数异常"),
            @ApiResponse(code = 1011001002, message = "应用编码不能为空,请检查code参数"),
            @ApiResponse(code = 1011001003, message = "应用编码错误"),
            @ApiResponse(code = 200, message = "正常业务请求（请求成功）"),
            @ApiResponse(code = 500, message = "网络异常"),

            // ------认证相关的异常------
            @ApiResponse(code = 1011001, message = "账号或密码为空，请检查account或password参数"),
            @ApiResponse(code = 1011002, message = "账号或密码错误，请检查account或password参数"),
            @ApiResponse(code = 1011003, message = "验证码错误，请检查captcha参数"),
            @ApiResponse(code = 1011004, message = "请求token为空，请携带token访问本接口"),
            @ApiResponse(code = 1011005, message = "token格式不正确，token请以OrderWay开头，并且OrderWay后边带一个空格"),
            @ApiResponse(code = 1011006, message = "帐号登录过期，请重新登录"),
            @ApiResponse(code = 1011007, message = "账号被冻结，请联系管理员"),
            @ApiResponse(code = 1011008, message = "登录已过期，请重新登录"),
            @ApiResponse(code = 1011009, message = "无登录用户"),

//            // -----服务器内部相关异常-----
//            @ApiResponse(code = 1, message = "当前请求参数为空或数据缺失，请联系管理员"),
//            @ApiResponse(code = 2, message = "服务器出现异常，请联系管理员"),
//            @ApiResponse(code = 3, message = "常量获取存在空值，请检查sys_config中是否配置"),
//
//            // -----系统用户相关异常-----
//            @ApiResponse(code = 1, message = "用户不存在"),
//            @ApiResponse(code = 2, message = "账号已存在，请检查account参数"),
//            @ApiResponse(code = 3, message = "原密码错误，请检查password参数"),
//            @ApiResponse(code = 4, message = "新密码与原密码相同，请检查newPassword参数"),
//            @ApiResponse(code = 5, message = "不能删除超级管理员"),
//            @ApiResponse(code = 6, message = "不能修改超级管理员状态"),
//            @ApiResponse(code = 7, message = "手机号已存在，请检查phone参数"),

    })
    @PostMapping("/errorCode")
    public ResponseData errorCode() {
        return new SuccessResponseData(null);
    }
}
