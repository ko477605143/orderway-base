/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.notice.enums;

import com.orderway.core.annotion.ExpEnumType;
import com.orderway.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.orderway.core.factory.ExpEnumCodeFactory;
import com.orderway.system.bus.core.consts.SysExpEnumConstant;

/**
 * 系统应用相关异常枚举
 *
 * @author oderway
 * @date 2020/3/26 10:11
 */
@ExpEnumType(module = SysExpEnumConstant.GUNS_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_NOTICE_EXCEPTION_ENUM)
public enum SysNoticeExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 通知公告不存在
     */
    NOTICE_NOT_EXIST(1, "通知公告不存在"),

    /**
     * 编辑失败
     */
    NOTICE_CANNOT_EDIT(2, "编辑失败，通知公告非草稿状态时无法编辑"),

    /**
     * 状态格式错误
     */
    NOTICE_STATUS_ERROR(3, "状态格式错误，请检查status参数"),

    /**
     * 删除失败
     */
    NOTICE_CANNOT_DELETE(4, "删除失败，通知公告已发布或已删除");

    private final Integer code;

    private final String message;

    SysNoticeExceptionEnum(Integer code, String message) {
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
