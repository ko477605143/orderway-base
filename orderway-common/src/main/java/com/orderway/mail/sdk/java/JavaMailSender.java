/*
create by orderway   add time  20220909
 */
package com.orderway.mail.sdk.java;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.orderway.mail.api.MailSenderApi;
import com.orderway.mail.api.exception.MailException;
import com.orderway.mail.api.exception.enums.EmailExceptionEnum;
import com.orderway.mail.api.expander.EmailConfigExpander;
import com.orderway.mail.api.pojo.SendMailParam;

/**
 * 邮件发送器
 *
 * @author fengshuonan
 * @date 2020/6/9 22:54
 */
public class JavaMailSender implements MailSenderApi {

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), false, sendMailParam.getFiles());
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), true, sendMailParam.getFiles());
    }

    /**
     * 获取配置账号信息
     *
     * @return {@link MailAccount}
     * @author majianguo
     * @date 2021/8/16 13:57
     **/
    private MailAccount getConfigAccountInfo() {
        MailAccount mailAccount = new MailAccount();
        // 配置默认都从系统配置表获取
        mailAccount.setHost(EmailConfigExpander.getSmtpHost());
        mailAccount.setPort(EmailConfigExpander.getSmtpPort());
        mailAccount.setAuth(EmailConfigExpander.getSmtpAuthEnable());
        mailAccount.setUser(EmailConfigExpander.getSmtpUser());
        mailAccount.setPass(EmailConfigExpander.getSmtpPass());
        mailAccount.setFrom(EmailConfigExpander.getSmtpFrom());
        mailAccount.setStarttlsEnable(EmailConfigExpander.getStartTlsEnable());
        mailAccount.setSslEnable(EmailConfigExpander.getSSLEnable());
        mailAccount.setSocketFactoryPort(EmailConfigExpander.getSocketFactoryPort());
        mailAccount.setTimeout(EmailConfigExpander.getTimeout());
        mailAccount.setConnectionTimeout(EmailConfigExpander.getConnectionTimeout());
        return mailAccount;
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @author fengshuonan
     * @date 2018/7/8 下午6:41
     */
    private void assertSendMailParams(SendMailParam sendMailParam) {
        if (sendMailParam == null) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTos())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "收件人邮箱");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTitle())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件标题");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getContent())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件内容");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }
    }
}
