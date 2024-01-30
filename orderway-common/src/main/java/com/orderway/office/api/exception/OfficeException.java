/*
create by orderway   add time  20220909
 */
package com.orderway.office.api.exception;

import com.orderway.office.api.constants.OfficeConstants;
import com.orderway.rule.exception.AbstractExceptionEnum;
import com.orderway.rule.exception.base.ServiceException;

/**
 * Office模块异常
 *
 * @author luojie
 * @date 2020/11/4 10:15
 */
public class OfficeException extends ServiceException {

    public OfficeException(AbstractExceptionEnum exception) {
        super(OfficeConstants.OFFICE_MODULE_NAME, exception);
    }

    public OfficeException(String errorCode, String userTip) {
        super(OfficeConstants.OFFICE_MODULE_NAME, errorCode, userTip);
    }

}
