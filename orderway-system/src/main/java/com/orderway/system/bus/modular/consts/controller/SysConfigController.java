/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.consts.controller;

import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.Permission;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.consts.param.SysConfigParam;
import com.orderway.system.bus.modular.consts.service.SysConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 参数配置控制器
 *
 * @author oderway
 * @date 2020/4/13 22:46
 */
@RestController
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 分页查询配置列表
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:10
     */
    @Permission
    @GetMapping("/sysConfig/page")
    @BusinessLog(title = "系统参数配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.page(sysConfigParam));
    }

    /**
     * 系统参数配置列表
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:10
     */
    @Permission
    @GetMapping("/sysConfig/list")
    @BusinessLog(title = "系统参数配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.list(sysConfigParam));
    }

    /**
     * 查看系统参数配置
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:12
     */
    @Permission
    @GetMapping("/sysConfig/detail")
    @BusinessLog(title = "系统参数配置_详情", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysConfigParam.detail.class) SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.detail(sysConfigParam));
    }

    /**
     * 添加系统参数配置
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:11
     */
    @Permission
    @PostMapping("/sysConfig/add")
    @BusinessLog(title = "系统参数配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysConfigParam.add.class) SysConfigParam sysConfigParam) {
        sysConfigService.add(sysConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统参数配置
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:11
     */
    @Permission
    @PostMapping("/sysConfig/delete")
    @BusinessLog(title = "系统参数配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysConfigParam.delete.class) SysConfigParam sysConfigParam) {
        sysConfigService.delete(sysConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统参数配置
     *
     * @author oderway, fengshuonan
     * @date 2020/4/14 11:11
     */
    @Permission
    @PostMapping("/sysConfig/edit")
    @BusinessLog(title = "系统参数配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysConfigParam.edit.class) SysConfigParam sysConfigParam) {
        sysConfigService.edit(sysConfigParam);
        return new SuccessResponseData();
    }

}


