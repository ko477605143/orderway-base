//package com.orderway.system.bus.message.handler;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.nacos.common.util.UuidUtils;
//import com.orderway.system.bus.message.ex.RocketExceptionMQEnum;
//
//import com.orderway.system.bus.message.ex.RocketMQException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.cloud.commons.util.IdUtils;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Component
//@Slf4j
//public class MRocketHandler {
//    // 封装了各种类型的消息发送
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
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
//
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
//                        //success to do
//
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
//     * 发送同步顺序消息
//     * 因为broker会管理多个消息队列，hashKey的参数，主要就是用来计算选择队列的。
//     */
//    public void sendOrderlyMessage(List<Map> obj, String topic, String tag) {
//
//        if (StrUtil.isEmpty(topic)) {
//            throw new RocketMQException(RocketExceptionMQEnum.TOPIC_ERROR);
//        }
//        String param;
//        if (StrUtil.isEmpty(tag)) {
//            param = topic;
//        } else {
//            param = topic + ":" + tag;
//        }
//
//        String g_value = IdUtil.simpleUUID();
//        for (Map map : obj) {
//            Message message = MessageBuilder.withPayload(map).setHeader("KEYS", IdUtil.fastSimpleUUID()).build();
//            rocketMQTemplate.syncSendOrderly(param, message, "s_" + g_value);
//        }
//
//
//    }
//
//
//}
