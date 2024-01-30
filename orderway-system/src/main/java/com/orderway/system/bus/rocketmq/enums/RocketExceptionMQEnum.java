package com.orderway.system.bus.rocketmq.enums;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.consts.ExpEnumConstant;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;

@ExpEnumType(module = ExpEnumConstant.GUNS_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.PARAM_EXCEPTION_ENUM)
public enum RocketExceptionMQEnum implements AbstractBaseExceptionEnum {

    TOPIC_ERROR(2001,"topic主题参数异常"),
    PAYLOAD_ERROR(2002,"payload消息参数异常");
    private final Integer code;

    private final String message;

    RocketExceptionMQEnum(Integer code, String message) {
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
