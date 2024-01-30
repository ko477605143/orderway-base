/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.core.enums;

import lombok.Getter;

/**
 * 通知公告状态
 *
 * @author oderway
 * @date 2020/6/28 17:51
 */
@Getter
public enum NoticeStatusEnum {

    /**
     * 草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 发布
     */
    PUBLIC(1, "发布"),

    /**
     * 撤回
     */
    CANCEL(2, "撤回"),

    /**
     * 删除
     */
    DELETED(3, "删除");

    private final Integer code;

    private final String message;

    NoticeStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
