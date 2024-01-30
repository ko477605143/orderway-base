/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.role.controller;

import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.DataScope;
import com.orderway.core.annotion.Permission;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.auth.bus.modular.role.param.SysRoleParam;
import com.orderway.auth.bus.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 *
 * @author oderway
 * @date 2020/3/20 19:42
 */
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 查询系统角色
     *
     * @author oderway
     * @date 2020/3/28 14:45
     */
    @Permission
    @GetMapping("/sysRole/page")
    @BusinessLog(title = "系统角色_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.page(sysRoleParam));
    }

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @author oderway
     * @date 2020/4/5 16:45
     */
    @Permission
    @GetMapping("/sysRole/dropDown")
    @BusinessLog(title = "系统角色_下拉", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dropDown() {
        return new SuccessResponseData(sysRoleService.dropDown());
    }

    /**
     * 添加系统角色
     *
     * @author oderway
     * @date 2020/3/28 14:45
     */
    @Permission
    @PostMapping("/sysRole/add")
    @BusinessLog(title = "系统角色_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysRoleParam.add.class) SysRoleParam sysRoleParam) {
        sysRoleService.add(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统角色
     *
     * @author oderway
     * @date 2020/3/28 14:45
     */
    @Permission
    @PostMapping("/sysRole/delete")
    @BusinessLog(title = "系统角色_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysRoleParam.delete.class) SysRoleParam sysRoleParam) {
        sysRoleService.delete(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统角色
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @PostMapping("/sysRole/edit")
    @BusinessLog(title = "系统角色_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysRoleParam.edit.class) SysRoleParam sysRoleParam) {
        sysRoleService.edit(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统角色
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysRole/detail")
    @BusinessLog(title = "系统角色_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.detail(sysRoleParam));
    }

    /**
     * 授权菜单
     *
     * @author oderway
     * @date 2020/3/28 16:05
     */
    @Permission
    @PostMapping("/sysRole/grantMenu")
    @BusinessLog(title = "系统角色_授权菜单", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantMenu(@RequestBody @Validated(SysRoleParam.grantMenu.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantMenu(sysRoleParam);
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
    @PostMapping("/sysRole/grantData")
    @BusinessLog(title = "系统角色_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysRoleParam.grantData.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantData(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有菜单
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysRole/ownMenu")
    @BusinessLog(title = "系统角色_拥有菜单", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownMenu(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownMenu(sysRoleParam));
    }

    /**
     * 拥有数据
     *
     * @author oderway
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysRole/ownData")
    @BusinessLog(title = "系统角色_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownData(sysRoleParam));
    }

}
