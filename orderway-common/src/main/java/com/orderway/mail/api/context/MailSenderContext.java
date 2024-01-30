/*
create by orderway   add time  20220909
 */
package com.orderway.mail.api.context;

import cn.hutool.extra.spring.SpringUtil;
import com.orderway.mail.api.MailSenderApi;

/**
 * 邮件发送的api上下文
 *
 * @author fengshuonan
 * @date 2020/10/26 10:16
 */
public class MailSenderContext {

    /**
     * 获取邮件发送的接口
     *
     * @author fengshuonan
     * @date 2020/10/26 10:16
     */
    public static MailSenderApi me() {
        return SpringUtil.getBean(MailSenderApi.class);
    }

}
