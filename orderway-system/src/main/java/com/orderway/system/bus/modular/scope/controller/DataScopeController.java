package com.orderway.system.bus.modular.scope.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.orderway.core.pojo.base.param.BaseGroups;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.scope.entity.SysDataScope;
import com.orderway.system.bus.modular.scope.param.SysDataScopeParam;
import com.orderway.system.bus.modular.scope.service.DataScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "API_数据授权", tags = "API_数据授权")
@RequestMapping("/data_scope")
public class DataScopeController {

    @Resource
    private DataScopeService dataScopeService;

    @ApiOperation("新增权限")
    @PostMapping("/scope_add")
    public ResponseData scope_add(@RequestBody @Validated(BaseGroups.add.class) SysDataScopeParam param) {
        dataScopeService.add(param);
        return new SuccessResponseData();
    }

    @ApiOperation("查询权限")
    @PostMapping("/scope_sel")
    public ResponseData scope_sel(@RequestBody @Validated(BaseGroups.list.class) SysDataScopeParam param) {

        return new SuccessResponseData(dataScopeService.list(param));
    }

    @ApiOperation("统一权限控制")
    @PostMapping("/unify_scope_sel")
    public ResponseData unify_scope_sel_(@RequestBody @Validated(BaseGroups.list.class) SysDataScopeParam param) {
        return new SuccessResponseData(dataScopeService.unify_scope_sel_(param));
    }


}
