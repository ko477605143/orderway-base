/*
create by orderway   add time  20220909
 */
package com.orderway.office.api.exception.enums;

import com.orderway.office.api.constants.OfficeConstants;
import com.orderway.rule.constants.RuleConstants;
import com.orderway.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * Office模块相关异常枚举
 *
 * @author luojie
 * @date 2020/11/4 10:19
 */
@Getter
public enum OfficeExceptionEnum implements AbstractExceptionEnum {

    /**
     * Office操作异常
     */
    OFFICE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "01", "Office操作异常，具体信息为：{}"),

    /**
     * Excel导出 响应为空
     */
    OFFICE_EXCEL_EXPORT_RESPONSE_ISNULL(RuleConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "02", "Excel导出响应为空"),

    /**
     * Excel导出 实体类为空
     */
    OFFICE_EXCEL_EXPORT_ENTITY_CLASS_ISNULL(RuleConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "03", "实体类为空");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    OfficeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
