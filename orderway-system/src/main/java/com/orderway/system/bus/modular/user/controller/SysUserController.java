/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.user.controller;

import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.DataScope;
import com.orderway.core.annotion.Permission;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import com.orderway.system.bus.modular.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统用户控制器
 *
 * @author oderway
 * @date 2020/3/19 21:14
 */
@RestController
@Api(value = "API_用户信息", tags = "API_用户信息")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询系统用户
     *
     * @author oderway
     * @date 2020/3/20 21:00
     */
    @Permission
    @DataScope
    @GetMapping("/sysUser/page")
    @ApiOperation("查询用户")
    @BusinessLog(title = "系统用户_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.page(sysUserParam));
    }

    /**
     * 增加系统用户
     *
     * @author oderway
     * @date 2020/3/23 16:28
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/add")
    @ApiOperation("新增用户")
    @BusinessLog(title = "系统用户_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysUserParam.add.class) SysUserParam sysUserParam) {
        sysUserService.add(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统用户
     *
     * @author oderway
     * @date 2020/3/23 16:28
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/delete")
    @ApiOperation("删除用户")
    @BusinessLog(title = "系统用户_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysUserParam.delete.class) SysUserParam sysUserParam) {
        sysUserService.delete(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统用户
     *
     * @author oderway
     * @date 2020/3/23 16:28
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/edit")
    @ApiOperation("编辑用户")
    @BusinessLog(title = "系统用户_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysUserParam.edit.class) SysUserParam sysUserParam) {
        sysUserService.edit(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统用户
     *
     * @author oderway
     * @date 2020/3/23 16:28
     */
    @Permission
    @GetMapping("/sysUser/detail")
    @BusinessLog(title = "系统用户_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.detail(sysUserParam));
    }

    /**
     * 修改状态
     *
     * @author oderway
     * @date 2020/5/25 14:32
     */
    @Permission
    @PostMapping("/sysUser/changeStatus")
    @ApiOperation("修改用户状态 0正常，1停用")
    @BusinessLog(title = "系统用户_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysUserParam.changeStatus.class) SysUserParam sysUserParam) {
        sysUserService.changeStatus(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 授权角色
     *
     * @author oderway
     * @date 2020/3/28 16:05
     */
    @Permission
    @DataScope
    @ApiOperation("授权角色")
    @PostMapping("/sysUser/grantRole")
    @BusinessLog(title = "系统用户_授权角色", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantRole(@RequestBody @Validated(SysUserParam.grantRole.class) SysUserParam sysUserParam) {
        sysUserService.grantRole(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author oderway
     * @date 2020/3/28 16:05
     */
    @Permission
    @DataScope
    @ApiOperation("授权数据")
    @PostMapping("/sysUser/grantData")
    @BusinessLog(title = "系统用户_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysUserParam.grantData.class) SysUserParam sysUserParam) {
        sysUserService.grantData(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 更新信息
     *
     * @author oderway
     * @date 2020/4/1 14:27
     */
    @PostMapping("/sysUser/updateInfo")
    @ApiOperation("编辑用户")
    @BusinessLog(title = "系统用户_更新信息", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateInfo(@RequestBody @Validated(SysUserParam.updateInfo.class) SysUserParam sysUserParam) {
        sysUserService.updateInfo(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 修改密码
     *
     * @author oderway
     * @date 2020/4/1 14:42
     */
    @PostMapping("/sysUser/updatePwd")
    @ApiOperation("修改密码")
    @BusinessLog(title = "系统用户_修改密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updatePwd(@RequestBody @Validated(SysUserParam.updatePwd.class) SysUserParam sysUserParam) {
        sysUserService.updatePwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有角色
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysUser/ownRole")
    @BusinessLog(title = "系统用户_拥有角色", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownRole(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownRole(sysUserParam));
    }

    /**
     * 拥有数据
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysUser/ownData")
    @BusinessLog(title = "系统用户_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownData(sysUserParam));
    }

    /**
     * 重置密码
     *
     * @author oderway
     * @date 2020/4/1 14:42
     */
    @Permission
    @PostMapping("/sysUser/resetPwd")
    @ApiOperation("重置密码")
    @BusinessLog(title = "系统用户_重置密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData resetPwd(@RequestBody @Validated(SysUserParam.resetPwd.class) SysUserParam sysUserParam) {
        sysUserService.resetPwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 修改头像
     *
     * @author oderway
     * @date 2020/6/28 15:19
     */
    @PostMapping("/sysUser/updateAvatar")
    @BusinessLog(title = "系统用户_修改头像", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateAvatar(@RequestBody @Validated(SysUserParam.updateAvatar.class) SysUserParam sysUserParam) {
        sysUserService.updateAvatar(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 导出系统用户
     *
     * @author oderway
     * @date 2020/6/30 16:07
     */
    @Permission
    @GetMapping("/sysUser/export")
    @BusinessLog(title = "系统用户_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysUserParam sysUserParam) {
        sysUserService.export(sysUserParam);
    }


    /**
     * 用户选择器
     *
     * @author oderway
     * @date 2020/7/3 13:17
     */
    @Permission
    @GetMapping("/sysUser/selector")
    @BusinessLog(title = "系统用户_选择器", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selector(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.selector(sysUserParam));
    }


    @GetMapping("/sysUser/sel_user_test")
    @BusinessLog(title = "系统用户_测试_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData sel_user_test() {
        return new SuccessResponseData(sysUserService.sel_user_test());
    }

}
