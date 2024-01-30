package com.orderway.system.bus.rocketmq.enums;

import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import lombok.Getter;

@Getter
public class RocketMQException extends RuntimeException{

    private final Integer code;

    private final String errorMessage;

    public RocketMQException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
