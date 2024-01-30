package com.orderway.system.bus.modular.auth.controller;

import com.orderway.core.pojo.base.param.BaseParam;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.auth.handler.HandlerNewVService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.user.param.SyncUserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 系统控制器
 *
 * @author oderway
 * @date 2020/3/11 12:20
 */
@ApiSort(value = 2)
@Api(value = "基础信息操作扩展", tags = "基础信息操作扩展")
@RestController
@RequestMapping("/handler/ext")
public class ExtHandlerController {

    @Resource
    private AuthService authService;

    @Resource
    private HandlerNewVService handlerNewVService;

    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "同步创建用户信息")
    @RequestMapping(value = "/addUserMsg", method = {RequestMethod.POST})
    public ResponseData addUserMsg(@RequestBody @Validated(BaseParam.add.class) SyncUserParam syncUserParam) {
        authService.addUserMsg(syncUserParam);
        return new SuccessResponseData();
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "同步更新用户信息")
    @RequestMapping(value = "/updateUserMsg", method = {RequestMethod.POST})
    public ResponseData updateUserMsg(HttpServletRequest request) {
        //注册用户基础信息的状态更新 不能登录（主要是更新这个）
        //用户名  工号  手机号不能更新（更新了缓存有影响）

        return new SuccessResponseData(null);
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "查询角色信息")
    @RequestMapping(value = "/selAllRole", method = {RequestMethod.POST})
    public ResponseData selAllRole(HttpServletRequest request) {
        return new SuccessResponseData(handlerNewVService.getSysLoginUserRoles());
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "查询机构信息")
    @RequestMapping(value = "/selAllOrg", method = {RequestMethod.POST})
    public ResponseData selAllOrg(HttpServletRequest request) {
        return new SuccessResponseData(handlerNewVService.getLoginUserOrg());
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "查询职位信息")
    @RequestMapping(value = "/selAllZW", method = {RequestMethod.POST})
    public ResponseData selAllZW(HttpServletRequest request) {
        return new SuccessResponseData(handlerNewVService.getLoginUserPos());
    }


}
