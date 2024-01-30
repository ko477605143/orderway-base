package com.orderway.system.bus.modular.emp.enums;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;
import com.orderway.system.bus.core.consts.SysExpEnumConstant;

@ExpEnumType(module = SysExpEnumConstant.GUNS_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_USER_EXCEPTION_ENUM)
public enum SysEmpExceptionEnum implements AbstractBaseExceptionEnum {

    EMP_JOBNUM_REPEAT(1,"员工工号已存在");
    private final Integer code;

    private final String message;

    SysEmpExceptionEnum(Integer code, String message) {
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
