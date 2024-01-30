/*
create by orderway   add time  20220909
 */
package com.orderway.mail.api;

import com.orderway.mail.api.pojo.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @author fengshuonan
 * @date 2020/10/23 17:30
 */
public interface MailSenderApi {

    /**
     * 发送普通邮件
     *
     * @param sendMailParam 发送邮件的参数
     * @author fengshuonan
     * @date 2020/10/23 17:30
     */
    void sendMail(SendMailParam sendMailParam);

    /**
     * 发送html的邮件
     *
     * @param sendMailParam 发送邮件的参数
     * @author fengshuonan
     * @date 2020/10/23 17:30
     */
    void sendMailHtml(SendMailParam sendMailParam);

}
