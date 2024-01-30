package com.orderway.system.bus.rocketmq.param;

import lombok.Data;

@Data
public class MqParam {

    private String topic;
    private String tag;
    private Object payload;
    private Long timeout;
    private Integer lvevl;
}
