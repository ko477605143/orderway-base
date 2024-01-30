//package com.orderway.system.bus.rocketmq.handler;
//
//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.nacos.common.util.UuidUtils;
//import com.orderway.core.pojo.response.ResponseData;
//import com.orderway.core.pojo.response.SuccessResponseData;
//import com.orderway.system.bus.rocketmq.enums.RocketExceptionMQEnum;
//import com.orderway.system.bus.rocketmq.enums.RocketMQException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//
//import javax.annotation.Resource;
//import java.nio.charset.StandardCharsets;
//
//@Component
//@Slf4j
//public class ProducerHandler {
//
//    // 封装了各种类型的消息发送
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    /**
//     * 发送同步消息
//     */
//    public void sendSyncMessage(String topic, String tag, Object payload) {
//        /**
//         * Header 中含有KEYS 则设置成message中的key
//         * topic + ":" + tag
//         */
//        String param = this.checkParam(topic, tag, payload);
//        Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload)).setHeader("KEYS", IdUtil.simpleUUID()).build();
//
//        rocketMQTemplate.syncSend(param, message);
//
//    }
//
//    /**
//     * 发送异步消息
//     */
//    public void sendAsyncMessage(String topic, String tag, Object payload) {
//        String param = this.checkParam(topic, tag, payload);
//        Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload)).setHeader("KEYS", IdUtil.simpleUUID()).build();
//
//        rocketMQTemplate.asyncSend(param, message,
//                new SendCallback() {
//                    // 异步发送成功
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        log.info("发送成功！！");
//                    }
//
//                    // 异步发送失败
//                    @Override
//                    public void onException(Throwable throwable) {
//                        log.error("发送失败！！");
//                    }
//                });
//
//    }
//
//    /**
//     * 发送单向消息
//     * 发送单向消息是指producer向 broker 发送消息，执行 API 时直接返回，不等待broker 服务器的结果 。
//     * 这种方式主要用在不特别关心发送结果的场景，举例：日志发送；
//     */
//    public void sendOneWayMessage(String topic, String tag, Object payload) {
//        String param = this.checkParam(topic, tag, payload);
//        Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload)).setHeader("KEYS", IdUtil.simpleUUID()).build();
//        rocketMQTemplate.sendOneWay(param, message);
//    }
//
//    /**
//     * 发送同步延迟消息
//     * 支持定时的延迟消息，但是不支持任意时间精度，仅支持特定的 level，例如定时 5s，
//     * 10s， 1m 等。其中，level=0 级表示不延时，level=1 表示 1 级延时，level=2 表示 2 级延时，以此类推。
//     * Level=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//     */
//    public void sendSyncDelayMessage(String topic, String tag, Object payload) {
//        String param = this.checkParam(topic, tag, payload);
//        Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload)).setHeader("KEYS", IdUtil.simpleUUID()).build();
//
//        SendResult level1 = rocketMQTemplate.syncSend(param, message, 3000, 3);
//        log.info(level1.toString() + "level1");
//    }
//
//    /**
//     * 发送异步延迟消息
//     */
//    public void sendAsyncDelayMessage(String topic, String tag, Object payload) {
//        String param = this.checkParam(topic, tag, payload);
//        Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload)).setHeader("KEYS", IdUtil.simpleUUID()).build();
//
//        rocketMQTemplate.asyncSend(param, message,
//                new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        log.info("发送成功！！");
//                    }
//
//                    @Override
//                    public void onException(Throwable throwable) {
//                        log.info("发送失败！！");
//                    }
//                }, 3000L, 3);
//    }
//
//    public void sendTransactionMessage(String topic, String tag, Object payload) {
//        String param = this.checkParam(topic, tag, payload);
//        String[] args = {"1", "2", "3"};
//        /**
//         * 参数1 topic 主题: tag 标签
//         * 参数2 消息内容
//         * 参数3 发送消息时传递的参数 通过该参数指定发送到哪个队列
//         */
//        for (String arg : args) {
//            Message message = MessageBuilder.withPayload(JSONObject.toJSON(payload) + arg).setHeader("KEYS", IdUtil.simpleUUID()).build();
//
//            rocketMQTemplate.sendMessageInTransaction(param, message, "rocketmq " + arg);
//        }
//    }
//
//    /**
//     * 参数校验
//     *
//     * @param topic 主题
//     * @param tag   标签
//     * @return param 主题 : tag
//     */
//    private String checkParam(String topic, String tag, Object payload) {
//        String param;
//        if (ObjectUtil.isNull(payload)) {
//            throw new RocketMQException(RocketExceptionMQEnum.PAYLOAD_ERROR);
//        }
//
//        if (StrUtil.isEmpty(topic)) {
//            throw new RocketMQException(RocketExceptionMQEnum.TOPIC_ERROR);
//        }
//        if (StrUtil.isEmpty(tag)) {
//            param = topic;
//        } else {
//            param = topic + ":" + tag;
//        }
//        return param;
//    }
//}
//
//
//
//
//
