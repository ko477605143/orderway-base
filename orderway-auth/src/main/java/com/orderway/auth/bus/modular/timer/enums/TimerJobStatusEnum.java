/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.timer.enums;

import lombok.Getter;

/**
 * 定时任务的状态
 *
 * @author oderway
 * @date 2020/6/30 20:44
 */
@Getter
public enum TimerJobStatusEnum {

    /**
     * 启动状态
     */
    RUNNING(1),

    /**
     * 停止状态
     */
    STOP(2);

    private final Integer code;

    TimerJobStatusEnum(int code) {
        this.code = code;
    }

}