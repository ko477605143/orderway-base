package com.orderway.system.interfaces;

import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;

@Api(value = "RPC服务测试",tags = "RPC服务测试")
@FeignClient("system")
public interface ISystem {


}
