/*
create by   orderway   add time 20220909
 */
package com.orderway.core.exception.enums;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.consts.ExpEnumConstant;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;

/**
 * 服务器内部相关异常枚举
 *
 * @author oderway
 * @date 2020/3/18 19:19
 */
@ExpEnumType(module = ExpEnumConstant.GUNS_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.SERVER_EXCEPTION_ENUM)
public enum ServerExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 当前请求参数为空或数据缺失
     */
    REQUEST_EMPTY(1, "当前请求参数为空或数据缺失，请联系管理员"),

    /**
     * 服务器出现未知异常
     */
    SERVER_ERROR(2, "服务器出现异常，请联系管理员"),

    /**
     * 常量获取存在空值
     */
    CONSTANT_EMPTY(3, "常量获取存在空值，请检查sys_config中是否配置");

    private final Integer code;

    private final String message;

    ServerExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
