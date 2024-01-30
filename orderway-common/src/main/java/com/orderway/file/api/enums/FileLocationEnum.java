/*
create by orderway   add time  20220909
 */
package com.orderway.file.api.enums;

import lombok.Getter;

/**
 * 文件存储位置
 *
 * @author oderway
 * @date 2020/6/7 22:24
 */
@Getter
public enum FileLocationEnum {

    /**
     * 阿里云
     */
    ALIYUN(1),

    /**
     * 腾讯云
     */
    TENCENT(2),

    /**
     * minio服务器
     */
    MINIO(3),

    /**
     * 本地
     */
    LOCAL(4),

    /**
     * 数据库中
     */
    DB(5);

    private final Integer code;

    FileLocationEnum(int code) {
        this.code = code;
    }

}
