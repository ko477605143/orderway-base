/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus;

import cn.hutool.log.Log;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SpringBoot方式启动类
 *
 * @author oderway
 * @date 2017/5/21 12:06
 */

//@NacosPropertySource(dataId = "WARE_ID", autoRefreshed = true)
@SpringBootApplication
//@EnableNacosDiscovery
//@EnableFeignClients
public class OrderWayAuthApplication {

    private static final Log log = Log.get();




    public static void main(String[] args) {
        SpringApplication.run(OrderWayAuthApplication.class, args);
        log.info(">>> " + OrderWayAuthApplication.class.getSimpleName() + " is success!");
    }

}
