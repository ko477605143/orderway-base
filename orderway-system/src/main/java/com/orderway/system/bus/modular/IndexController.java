/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.orderway.core.consts.CommonConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 *
 * @author oderway
 * @date 2020/3/18 11:20
 */
@RestController
public class IndexController {

    /**
     * 访问首页，提示语
     *
     * @author oderway
     * @date 2020/4/8 19:27
     */
    @RequestMapping("/")
    public String index() {
        return CommonConstant.INDEX_TIPS;
    }


    @NacosInjected
    private NamingService namingService;

    @GetMapping("nacos_test")
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}
