package com.orderway.auth.bus.modular.data;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.consts.ExpEnumConstant;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;

@ExpEnumType(module = ExpEnumConstant.GUNS_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.AUTH_EXCEPTION_ENUM)
public enum AppHandlerExceptionEnum implements AbstractBaseExceptionEnum {


    PARAM_ERR(8001 ,"请求参数异常！"),
    TOKEN_ERR(8001 ,"token失效！");

    private final Integer code;

    private final String message;

    AppHandlerExceptionEnum(Integer code, String message) {
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
