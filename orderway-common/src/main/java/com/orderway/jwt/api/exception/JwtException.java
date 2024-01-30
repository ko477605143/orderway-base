/*
create by orderway   add time  20220909
 */
package com.orderway.jwt.api.exception;

import cn.hutool.core.util.StrUtil;
import com.orderway.jwt.api.constants.JwtConstants;
import com.orderway.rule.exception.AbstractExceptionEnum;
import com.orderway.rule.exception.base.ServiceException;

/**
 * jwt异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class JwtException extends ServiceException {

    public JwtException(AbstractExceptionEnum exception, Object... params) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
