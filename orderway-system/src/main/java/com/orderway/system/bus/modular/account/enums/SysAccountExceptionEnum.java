package com.orderway.system.bus.modular.account.enums;

import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;

/**
 * 子账号相关异常枚举
 *
 */
public enum SysAccountExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 编码已存在
     */
    ACCOUNT_CODE_REPEAT(1,"编码已存在,请检查code参数"),

    /**
     * 子账户信息不存在
     */
    ACCOUNT_NOT_EXIST(2,"子账户信息不存在"),

    /**
     * 子账户原密码错误
     */
    ACCOUNT_PASSWORD_ERR(3,"原密码错误,请检查password属性"),

    /**
     * 旧密码与密码相同
     */
    ACCOUNT_PWD_REPEAT(4, "新密码与原密码相同，请检查newPassword参数");

    private final Integer code;

    private final String message;

    SysAccountExceptionEnum(Integer code, String message) {
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
