package com.orderway.auth.bus.modular.data;

import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import lombok.Getter;

@Getter
public class HandlerException extends RuntimeException {

    private final Integer code;

    private final String errorMessage;

    public HandlerException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
