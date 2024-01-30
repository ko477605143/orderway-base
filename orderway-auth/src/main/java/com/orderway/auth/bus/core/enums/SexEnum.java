/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.core.enums;

import lombok.Getter;

/**
 * 性别枚举
 *
 * @author oderway
 * @date 2020/5/28 10:51
 */
@Getter
public enum SexEnum {

    /**
     * 男
     */
    MAN(1, "男"),

    /**
     * 女
     */
    WOMAN(2, "女"),

    /**
     * 未知
     */
    NONE(3, "未知");

    private final Integer code;

    private final String message;

    SexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
