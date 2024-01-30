/*
create by orderway   add time  20220909
 */
package com.orderway.scanner.api.enums;

import lombok.Getter;

/**
 * 字段元数据类型
 *
 * @author majianguo
 * @date 2022/1/11 9:25
 */
@Getter
public enum FieldMetadataTypeEnum {

    /**
     * 字段
     */
    FIELD(1),

    /**
     * 泛型
     */
    GENERIC(2);

    FieldMetadataTypeEnum(Integer code) {
        this.code = code;
    }

    private final Integer code;

}
