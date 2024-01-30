/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.config;

import com.orderway.auth.bus.core.aop.BusinessLogAop;
import com.orderway.auth.bus.core.aop.DataScopeAop;
import com.orderway.auth.bus.core.aop.PermissionAop;
import com.orderway.auth.bus.core.aop.WrapperAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 切面配置
 *
 * @author oderway
 * @date 2020/3/18 11:25
 */
@Configuration
public class AopConfig {

    /**
     * 日志切面
     *
     * @author oderway
     * @date 2020/3/20 14:10
     */
    @Bean
    public BusinessLogAop businessLogAop() {
        return new BusinessLogAop();
    }

    /**
     * 权限切面
     *
     * @author oderway
     * @date 2020/3/23 17:36
     */
    @Bean
    public PermissionAop permissionAop() {
        return new PermissionAop();
    }

    /**
     * 数据范围切面
     *
     * @author oderway
     * @date 2020/4/6 13:47
     */
    @Bean
    public DataScopeAop dataScopeAop() {
        return new DataScopeAop();
    }

    /**
     * 结果包装的aop
     *
     * @author fengshuonan
     * @date 2020/7/24 22:18
     */
    @Bean
    public WrapperAop wrapperAop() {
        return new WrapperAop();
    }
}
