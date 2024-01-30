/*
create by orderway   add time  20220909
 */
package com.orderway.config.api;

import com.orderway.config.api.pojo.ConfigInitItem;

import java.util.List;

/**
 * 配置初始化的策略
 *
 * @author fengshuonan
 * @date 2021/7/8 17:33
 */
public interface ConfigInitStrategyApi {

    /**
     * 获取需要被初始化的配置集合
     *
     * @return 需要被初始化的配置集合
     * @author fengshuonan
     * @date 2021/7/8 17:40
     */
    List<ConfigInitItem> getInitConfigs();

}
