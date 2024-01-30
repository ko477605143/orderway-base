/*
create by orderway   add time  20220909
 */
package com.orderway.file.api.context;

import cn.hutool.extra.spring.SpringUtil;
import com.orderway.file.api.FileOperatorApi;

/**
 * 文件操作api的上下文
 *
 * @author fengshuonan
 * @date 2020/10/26 10:30
 */
public class FileContext {

    /**
     * 获取文件操作接口
     *
     * @author fengshuonan
     * @date 2020/10/17 14:30
     */
    public static FileOperatorApi me() {
        return SpringUtil.getBean(FileOperatorApi.class);
    }

}
