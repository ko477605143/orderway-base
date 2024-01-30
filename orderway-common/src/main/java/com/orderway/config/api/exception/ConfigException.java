/*
create by orderway   add time  20220909
 */
package com.orderway.config.api.exception;

import com.orderway.base.exception.AbstractExceptionEnum;
import com.orderway.base.exception.base.ServiceException;
import com.orderway.config.api.constants.ConfigConstants;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class ConfigException extends ServiceException {

    public ConfigException(String errorCode, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, errorCode, userTip);
    }

    public ConfigException(AbstractExceptionEnum exception) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception);
    }

    public ConfigException(AbstractExceptionEnum exception, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
