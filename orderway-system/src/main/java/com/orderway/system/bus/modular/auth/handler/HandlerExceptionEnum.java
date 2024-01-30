package com.orderway.system.bus.modular.auth.handler;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.consts.ExpEnumConstant;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;

@ExpEnumType(module = ExpEnumConstant.GUNS_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.AUTH_EXCEPTION_ENUM)
public enum HandlerExceptionEnum implements AbstractBaseExceptionEnum {


    PARAM_ERR(1001 ,"用户登录请求参数异常！"),

    PARAM_CODE_NULL(1002,"应用编码不能为空,请检查code参数"),

    PARAM_CODE_ERR(1003,"应用编码错误");

    private final Integer code;

    private final String message;

    HandlerExceptionEnum(Integer code, String message) {
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
