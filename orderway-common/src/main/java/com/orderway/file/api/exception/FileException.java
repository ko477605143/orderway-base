/*
create by orderway   add time  20220909
 */
package com.orderway.file.api.exception;

import cn.hutool.core.util.StrUtil;
import com.orderway.file.api.constants.FileConstants;
import com.orderway.base.exception.AbstractExceptionEnum;
import com.orderway.base.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class FileException extends ServiceException {

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, Object... params) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
