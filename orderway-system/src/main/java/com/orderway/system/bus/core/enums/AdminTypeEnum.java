/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.core.enums;

import lombok.Getter;

/**
 * 管理员类型枚举
 *
 * @author oderway
 * @date 2020/4/5 10:23
 */
@Getter
public enum AdminTypeEnum {

    /**
     * 超级管理员
     */
    SUPER_ADMIN(1, "超级管理员"),

    /**
     * 非管理员
     */
    NONE(2, "非管理员");

    private final Integer code;

    private final String message;

    AdminTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
