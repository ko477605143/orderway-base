/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.sms.param;

import com.orderway.auth.bus.modular.sms.enums.SmsSendSourceEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 验证短信的参数
 *
 * @author oderway
 * @date 2018/7/5 21:19
 */
@Data
public class SysSmsVerifyParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phoneNumbers;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号不能为空")
    private String templateCode;

    /**
     * 来源
     */
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

}
