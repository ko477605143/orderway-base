/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.sms.enums;

import lombok.Getter;

/**
 * 短信发送业务枚举
 *
 * @author oderway
 * @date 2018/5/6 12:30
 */
@Getter
public enum SmsSendSourceEnum {

    /**
     * APP
     */
    APP(1),

    /**
     * PC
     */
    PC(2),

    /**
     * OTHER
     */
    OTHER(3);

    private final Integer code;

    SmsSendSourceEnum(Integer code) {
        this.code = code;
    }
}
