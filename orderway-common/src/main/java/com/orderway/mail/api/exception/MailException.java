/*
create by orderway   add time  20220909
 */
package com.orderway.mail.api.exception;

import com.orderway.base.exception.AbstractExceptionEnum;
import com.orderway.base.exception.base.ServiceException;
import com.orderway.mail.api.constants.MailConstants;
import lombok.Getter;

/**
 * 邮件发送异常
 *
 * @author fengshuonan
 * @date 2018-07-06-下午3:00
 */
@Getter
public class MailException extends ServiceException {

    public MailException(String errorCode, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, errorCode, userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum.getErrorCode(), userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum);
    }

}
