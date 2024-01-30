/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.pos.controller;

import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.Permission;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.auth.bus.modular.pos.param.SysPosParam;
import com.orderway.auth.bus.modular.pos.service.SysPosService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统职位控制器
 *
 * @author oderway
 * @date 2020/3/20 19:44
 */
@RestController
public class SysPosController {

    @Resource
    private SysPosService sysPosService;

    /**
     * 查询系统职位
     *
     * @author oderway
     * @date 2020/3/26 10:20
     */
    @Permission
    @GetMapping("/sysPos/page")
    @BusinessLog(title = "系统职位_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.page(sysPosParam));
    }

    /**
     * 系统职位列表
     *
     * @author yubaoshan
     * @date 2020/6/21 23:38
     */
    @Permission
    @GetMapping("/sysPos/list")
    @BusinessLog(title = "系统职位_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.list(sysPosParam));
    }

    /**
     * 添加系统职位
     *
     * @author oderway
     * @date 2020/3/26 19:03
     */
    @Permission
    @PostMapping("/sysPos/add")
    @BusinessLog(title = "系统职位_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysPosParam.add.class) SysPosParam sysPosParam) {
        sysPosService.add(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统职位
     *
     * @author oderway
     * @date 2020/3/25 14:54
     */
    @Permission
    @PostMapping("/sysPos/delete")
    @BusinessLog(title = "系统职位_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysPosParam.delete.class) SysPosParam sysPosParam) {
        sysPosService.delete(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统职位
     *
     * @author oderway
     * @date 2020/3/25 14:54
     */
    @Permission
    @PostMapping("/sysPos/edit")
    @BusinessLog(title = "系统职位_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysPosParam.edit.class) SysPosParam sysPosParam) {
        sysPosService.edit(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统职位
     *
     * @author oderway
     * @date 2020/3/26 9:49
     */
    @Permission
    @GetMapping("/sysPos/detail")
    @BusinessLog(title = "系统职位_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysPosParam.detail.class) SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.detail(sysPosParam));
    }
}
