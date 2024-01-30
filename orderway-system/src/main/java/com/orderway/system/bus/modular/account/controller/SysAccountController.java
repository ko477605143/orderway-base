package com.orderway.system.bus.modular.account.controller;

import cn.hutool.core.lang.Dict;
import com.mysql.cj.util.StringUtils;
import com.orderway.core.pojo.base.param.BaseParam;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.account.param.SysAccountParam;
import com.orderway.system.bus.modular.account.service.SysAccountService;
import com.orderway.system.bus.modular.auth.handler.HandlerException;
import com.orderway.system.bus.modular.auth.handler.HandlerExceptionEnum;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.ws.Response;

@RestController
@Api(value = "子账户管理基础操作", tags = "子账户管理基础操作")
@RequestMapping("/sysAccount")
public class SysAccountController {

    @Resource
    private SysAccountService sysAccountService;

    @Resource
    private AuthService authService;

    /**
     * 新增多账户信息
     */
    @RequestMapping(value = "/addAccount", method = {RequestMethod.POST})
    @ApiOperation("新增子账户")
    public ResponseData add(@RequestBody @Validated(SysAccountParam.add.class) SysAccountParam sysAccountParam) {
        sysAccountService.add(sysAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 根据用户id
     * 查询用户对应子账号信息
     */
    @GetMapping("/getUserAccount")
    @ApiOperation("根据用户id查询子账户信息")
    public ResponseData getUserAccount(@Validated(BaseParam.detail.class) SysAccountParam sysAccountParam) {
        return new SuccessResponseData(sysAccountService.listById(sysAccountParam.getUserId()));
    }

    /**
     * 根据账户表Id 修改子账户数据
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    @ApiOperation("根据子账户id修改子账户信息")
    public ResponseData edit(@Validated(SysAccountParam.edit.class) SysAccountParam sysAccountParam) {
        sysAccountService.edit(sysAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 根据子账户id修改子账号密码
     *
     * @param sysAccountParam 参数信息
     * @return
     */
    // 参数提示
    @ApiImplicitParam(name = "param", value = "只需要传userId,password,newPassword")
    @RequestMapping(value = "/updatePwd", method = {RequestMethod.POST})
    @ApiOperation("根据子账户id修改子账户密码")
    public ResponseData updatePwd(@Validated(SysAccountParam.updatePwd.class) SysAccountParam sysAccountParam) {
        sysAccountService.updatePwd(sysAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 根据子账户id重置子账户密码
     *
     * @param sysAccountParam 参数信息
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = {RequestMethod.POST})
    @ApiOperation("根据子账户id重置子账户密码")
    public ResponseData resetPwd(@Validated(SysAccountParam.resetPwd.class) SysAccountParam sysAccountParam) {
        sysAccountService.resetPwd(sysAccountParam.getId());
        return new SuccessResponseData();
    }

    /**
     * 删除子账号
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ApiOperation("删除子账号信息")
    public ResponseData delete(@Validated(SysAccountParam.delete.class) SysAccountParam sysAccountParam) {
        sysAccountService.delete(sysAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 根据子账号id 找到用户
     */
    @RequestMapping(value = "/getUserByAccountId", method = {RequestMethod.POST})
    @ApiOperation("根据子账号id找到用户")
    public ResponseData getUserByAccountId(Long accountId) {
        return new SuccessResponseData(sysAccountService.getUserByAccountId(accountId));
    }

    /**
     *
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ApiOperation("根据子账号登入返回用户Token")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("account");
        String password = dict.getStr("password");
        String c_type = dict.getStr("c_type");

        if (StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(c_type)) {
            throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
        } else {
            if (c_type.equals("0")) {
            } else if (c_type.equals("1")) {
            } else if (c_type.equals("2")) {
            } else {
                throw new HandlerException(HandlerExceptionEnum.PARAM_ERR);
            }
        }
        String token = authService.sonLogin(account, password, c_type);
        return new SuccessResponseData(token);
    }
}





