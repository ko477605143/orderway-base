/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.role.enums;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;
import com.orderway.auth.bus.core.consts.SysExpEnumConstant;

/**
 * 系统角色相关异常枚举
 *
 * @author oderway
 * @date 2020/3/28 14:47
 */
@ExpEnumType(module = SysExpEnumConstant.GUNS_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_ROLE_EXCEPTION_ENUM)
public enum SysRoleExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 角色不存在
     */
    ROLE_NOT_EXIST(1, "角色不存在"),

    /**
     * 角色编码重复
     */
    ROLE_CODE_REPEAT(2, "角色编码重复，请检查code参数"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REPEAT(3, "角色名称重复，请检查name参数");

    private final Integer code;

    private final String message;

    SysRoleExceptionEnum(Integer code, String message) {
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
