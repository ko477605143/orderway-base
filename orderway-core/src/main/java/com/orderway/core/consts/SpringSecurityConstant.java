/*
create by   orderway   add time 20220909
 */
package com.orderway.core.consts;

/**
 * SpringSecurity相关常量
 *
 * @author oderway
 * @date 2020/3/18 17:49
 */
public interface SpringSecurityConstant {

    /**
     * 放开权限校验的接口
     */
    String[] NONE_SECURITY_URL_PATTERNS = {

            //前端的
            "/favicon.ico",

            //swagger相关的
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/configuration/ui",
            "/configuration/security",

            //后端的
            "/",
            "/nacos_test",
            "/customer",
            "/system/customer",
            "/system/login",
            "/testlogin",
            "/login",
            "/logout",
            "/handler/login",
            "/handler/logout",
            "/handler/new/login", // 2022.9.26 添加
            "/sysAccount/login", // 2022.9.30
            "/handler/ext/new/login",
            "/handler/app/getToken",
            //"/handler/app/checkToken",

            //文件的
            "/sysFileInfo/upload",
            "/sysFileInfo/download",
            "/sysFileInfo/preview",

            //druid的
            "/druid/**",

            //rocketmq
            "/rocketmq/**"
    };

}
