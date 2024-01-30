/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.monitor.service;

import com.orderway.auth.bus.modular.monitor.result.SysMachineResult;

/**
 * 系统属性监控service接口
 *
 * @author oderway
 * @date 2020/6/5 14:39
 */
public interface SysMachineService {

    /**
     * 系统属性监控
     *
     * @return 系统属性结果集
     * @author oderway
     * @date 2020/6/5 14:45
     */
    SysMachineResult query();
}
