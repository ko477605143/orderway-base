package com.orderway.system.bus.provider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@Api(value = "RPC服务测试",tags = "RPC服务测试")
@FeignClient("system")
public interface ProviderFeignClient {

    @ApiOperation(value = "查询我未提交的产品数")
    @RequestMapping("/system/customer")
    List<Map> sel_all_user();

}
