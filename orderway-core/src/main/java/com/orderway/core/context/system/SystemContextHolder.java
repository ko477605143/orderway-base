/*
create by   orderway   add time 20220909
 */
package com.orderway.core.context.system;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 使用system模块相关功能的接口
 *
 * @author oderway
 * @date 2020/5/6 14:56
 */
public class SystemContextHolder {

    public static SystemContext me() {
        return SpringUtil.getBean(SystemContext.class);
    }

}
