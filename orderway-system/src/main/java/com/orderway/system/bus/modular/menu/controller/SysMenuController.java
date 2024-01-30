/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.menu.controller;

import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.Permission;
import com.orderway.core.context.login.LoginContextHolder;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.system.bus.modular.auth.handler.HandlerSystemServices;
import com.orderway.system.bus.modular.menu.param.SysMenuParam;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.orderway.core.enums.LogAnnotionOpTypeEnum.TREE;

/**
 * 系统菜单控制器
 *
 * @author oderway
 * @date 2020/3/20 18:54
 */
@RestController
@Api(value = "API_菜单操作接口", tags = "API_菜单操作接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private HandlerSystemServices handlerSystemServices;
    /**
     * 系统菜单列表（树）
     *
     * @author oderway
     * @date 2020/3/20 21:23
     */
    @Permission
    @GetMapping("/sysMenu/list")
    @BusinessLog(title = "系统菜单_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.list(sysMenuParam));
    }

    /**
     * 添加系统菜单
     *
     * @author oderway
     * @date 2020/3/27 8:57
     */
    @Permission
    @PostMapping("/sysMenu/add")
    @BusinessLog(title = "系统菜单_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysMenuParam.add.class) SysMenuParam sysMenuParam) {
        sysMenuService.add(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统菜单
     *
     * @author oderway
     * @date 2020/3/27 8:58
     */
    @Permission
    @PostMapping("/sysMenu/delete")
    @BusinessLog(title = "系统菜单_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysMenuParam.delete.class) SysMenuParam sysMenuParam) {
        sysMenuService.delete(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统菜单
     *
     * @author oderway
     * @date 2020/3/27 8:59
     */
    @Permission
    @PostMapping("/sysMenu/edit")
    @BusinessLog(title = "系统菜单_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysMenuParam.edit.class) SysMenuParam sysMenuParam) {
        sysMenuService.edit(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统菜单
     *
     * @author oderway
     * @date 2020/3/27 9:01
     */
    @Permission
    @PostMapping("/sysMenu/detail")
    @BusinessLog(title = "系统菜单_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysMenuParam.detail.class) SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.detail(sysMenuParam));
    }

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @author oderway
     * @date 2020/3/27 15:55
     */
    @Permission
    @GetMapping("/sysMenu/tree")
    @BusinessLog(title = "系统菜单_树", opType = TREE)
    public ResponseData tree(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.tree(sysMenuParam));
    }

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @author oderway
     * @date 2020/3/27 15:55
     */
//    @Permission
    @GetMapping("/sysMenu/treeByApp")
    @ApiOperation(value = "根据应用code，获取菜单树")
    @BusinessLog(title = "系统菜单_树", opType = TREE)
    public ResponseData treeByApp(String application) {
        return new SuccessResponseData(sysMenuService.treeByApplication(application));
    }

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @author oderway
     * @date 2020/4/5 15:00
     */
    @Permission
    @GetMapping("/sysMenu/treeForGrant")
    @BusinessLog(title = "系统菜单_授权树", opType = TREE)
    public ResponseData treeForGrant(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.treeForGrant(sysMenuParam));
    }

    /**
     * 根据系统切换菜单
     *
     * @author oderway
     * @date 2020/4/19 15:50
     */
//    @PostMapping("/sysMenu/change")
//    @BusinessLog(title = "系统菜单_切换", opType = LogAnnotionOpTypeEnum.TREE)
//    public ResponseData change(@RequestBody @Validated(SysMenuParam.change.class) SysMenuParam sysMenuParam) {
//        Long sysLoginUserId = LoginContextHolder.me().getSysLoginUserId();
//        return new SuccessResponseData(sysMenuService.getLoginMenusAntDesign(sysLoginUserId, sysMenuParam.getApplication()));
//    }
    @PostMapping("/sysMenu/change")
    @BusinessLog(title = "系统菜单_切换", opType = TREE)
    public ResponseData change(@RequestBody @Validated(SysMenuParam.change.class) SysMenuParam sysMenuParam) {
        SysLoginUser sysLoginUser = handlerSystemServices.getSysLoginUser(HttpServletUtil.getRequest());
        System.out.println(sysLoginUser);
        return new SuccessResponseData(sysMenuService.getUserMenu(sysLoginUser.getId(),sysMenuParam.getApplication()));
    }
}
