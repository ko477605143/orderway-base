package com.orderway.auth.bus.provider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


//@Api(value = "产品Provider")
@FeignClient("system")
public interface ProviderFeignClient {

   // @ApiOperation(value = "查询我未提交的产品数")
    @RequestMapping("/system/customer")
    String index();

}
