/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.controller;

import cn.hutool.core.util.StrUtil;
import com.orderway.core.annotion.BusinessLog;
import com.orderway.core.annotion.Permission;
import com.orderway.core.enums.LogAnnotionOpTypeEnum;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.pojo.base.param.BaseParam;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;
import com.orderway.system.bus.modular.app.enums.SysAppExceptionEnum;
import com.orderway.system.bus.modular.app.param.SysAppParam;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.auth.mapper.DataHandlerMapper;
import com.orderway.system.bus.modular.dict.param.SysDictTypeParam;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 系统应用控制器
 *
 * @author oderway
 * @date 2020/3/20 21:25
 */
@RestController
@Api(value = "API_应用信息接口", tags = "API_应用信息接口")
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 查询系统应用
     *
     * @author oderway
     * @date 2020/3/20 21:23
     */
    @Permission
    @GetMapping("/sysApp/page")
    @BusinessLog(title = "系统应用_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.page(sysAppParam));
    }

    /**
     * 添加系统应用
     *
     * @author oderway
     * @date 2020/3/25 14:44
     */
    @ApiOperation("系统应用_增加")
    @Permission
    @PostMapping("/sysApp/add")
    @BusinessLog(title = "系统应用_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysAppParam.add.class) SysAppParam sysAppParam) {
        sysAppService.add(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统应用
     *
     * @author oderway
     * @date 2020/3/25 14:54
     */
    @ApiOperation("系统应用_删除")
    @Permission
    @PostMapping("/sysApp/delete")
    @BusinessLog(title = "系统应用_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysAppParam.delete.class) SysAppParam sysAppParam) {
        sysAppService.delete(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统应用
     *
     * @author oderway
     * @date 2020/3/25 14:54
     */
    @ApiOperation("系统应用_编辑")
    @Permission
    @PostMapping("/sysApp/edit")
    @BusinessLog(title = "系统应用_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysAppParam.edit.class) SysAppParam sysAppParam) {
        sysAppService.edit(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统应用
     *
     * @author oderway
     * @date 2020/3/26 9:49
     */
    @Permission
    @ApiOperation("系统应用_查看")
    @GetMapping("/sysApp/detail")
    @BusinessLog(title = "系统应用_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysAppParam.detail.class) SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @author oderway
     * @date 2020/4/19 14:55
     */
    @Permission
    @ApiOperation("系统应用_列表")
    @GetMapping("/sysApp/list")
    @BusinessLog(title = "系统应用_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.list(sysAppParam));
    }

    /**
     * 设为默认应用
     *
     * @author oderway
     * @date 2020/6/29 16:49
     */
    @Permission
    @ApiOperation("系统应用_设为默认应用")
    @PostMapping("/sysApp/setAsDefault")
    @BusinessLog(title = "系统应用_设为默认应用", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData setAsDefault(@RequestBody @Validated(SysAppParam.detail.class) SysAppParam sysAppParam) {
        sysAppService.setAsDefault(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 获取应用下拉框
     */
    @ApiOperation("获取客户端类型")
    @GetMapping("/sysApp/dropDown")
    @BusinessLog(title = "客户端类型_下拉框", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData clientType(@Validated(BaseParam.dropDown.class) SysDictTypeParam SysDictTypeParam) {
        return new SuccessResponseData(sysAppService.appDropDown(SysDictTypeParam));
    }


    /**
     * 根据条件查询系统应用 并分页
     *
     * @author oderway
     * @date 2020/3/20 21:23
     */
    @ApiOperation("根据条件查询并分页")
    @Permission
    @GetMapping("/sysApp/selectPage")
    @BusinessLog(title = "系统应用_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectPage(SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.selectAppByPage(sysAppParam));
    }

    /**
     * 获取所有的应用
     *
     * @author oderway
     * @date 2020/3/20 21:23
     */
    @ApiOperation("获取所有的应用")
    @GetMapping("/sysApp/selectAll")
    public ResponseData selectAll() {
        return new SuccessResponseData(sysAppService.selectAll());
    }

    @Autowired
    private DataHandlerMapper dataHandlerMapper;

    @ApiOperation("系统应用_app_id app_key 更新")
    @Permission
    @GetMapping("/sysApp/appIdAndKey/gen")
    @BusinessLog(title = "系统应用_app_id app_key 更新")
    public ResponseData update_app_id_key(Long id) {
        System.out.println("============================================================");
        System.out.println("=========>" + id);
        System.out.println("============================================================");
        String xxx = RandomStringUtils.randomNumeric(6);
        String app_id = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + xxx;
        String app_key = UUID.randomUUID().toString().replace("-", "");

        dataHandlerMapper.up_app_id_key(id,app_id,app_key);

        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author oderway
     * @date 2020/5/25 14:32
     */
    @Permission
    @PostMapping("/sysApp/changeStatus")
    @ApiOperation("修改状态 0正常，1停用")
    @BusinessLog(title = "系统用户_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysAppParam.changeStatus.class) SysAppParam sysAppParam) {
        sysAppService.changeStatus(sysAppParam);
        return new SuccessResponseData();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        String xxx = RandomStringUtils.randomNumeric(6);

        ;System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + xxx);
    }

}
