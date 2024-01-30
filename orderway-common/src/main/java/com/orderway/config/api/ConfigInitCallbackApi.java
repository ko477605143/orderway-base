/*
create by orderway   add time  20220909
 */
package com.orderway.config.api;

/**
 * 配置初始化回调
 *
 * @author majianguo
 * @date 2021/7/17 11:52
 */
public interface ConfigInitCallbackApi {

    /**
     * 初始化之前回调
     * <p>
     * 其他组件可以在配置初始化之前干一些事情，如生成配置文件等操作
     *
     * @author majianguo
     * @date 2021/7/17 11:54
     **/
    void initBefore();

    /**
     * 初始化之后回调
     * <p>
     * 其他组件可以在配置初始化之前干一些事情,如重新配置等操作
     *
     * @author majianguo
     * @date 2021/7/17 11:55
     **/
    void initAfter();
}
