/*
create by orderway   add time  20220909
 */
package com.orderway.scanner.api.enums;

import lombok.Getter;

/**
 * 请求参数类型
 *
 * @author fengshuonan
 * @date 2022/1/20 11:32
 */
@Getter
public enum ParamTypeEnum {

    /**
     * query param参数，例如：?field1=aaa&field2=bbb
     */
    QUERY_PARAM(1),

    /**
     * body param参数，请求是json传来的
     */
    REQUEST_BODY(2);

    ParamTypeEnum(Integer code) {
        this.code = code;
    }

    private final Integer code;

}
