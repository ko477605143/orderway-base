//package com.orderway.system.bus.rocketmq.controller;
//
//import cn.hutool.core.lang.Dict;
//import com.orderway.core.pojo.response.ResponseData;
//import com.orderway.core.pojo.response.SuccessResponseData;
//import com.orderway.system.bus.rocketmq.handler.ProducerHandler;
//import com.orderway.system.bus.rocketmq.param.MqParam;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//
///**
// * 消息生产者
// */
//public class MessageController {
//
//    // 封装了各种类型的消息发送
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Resource
//    private ProducerHandler producerHandler;
//
//    @PostMapping("/syncMessage")
//    @ApiOperation("发送同步消息")
//    public ResponseData sendSyncMessage(@RequestBody MqParam param) {
//        // param
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//
//        producerHandler.sendSyncMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    @ApiOperation("发送异步消息")
//    @PostMapping("/asyncMessage")
//    public ResponseData sendAsyncMessage(@RequestBody MqParam param) {
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//        producerHandler.sendAsyncMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    @ApiOperation("发送单向消息")
//    @RequestMapping(value = "/oneWay", method = RequestMethod.POST)
//    public ResponseData sendOneWayMessage(@RequestBody MqParam param) {
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//
//        producerHandler.sendOneWayMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    /**
//     * 发送同步顺序消息
//     * 因为broker会管理多个消息队列，hashKey的参数，主要就是用来计算选择队列的。
//     */
//    @ApiOperation("发送顺序消息")
//    @RequestMapping(value = "/orderlyMessage", method = RequestMethod.GET)
//    public void sendOrderlyMessage() {
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456231,创建", "98456231");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456231,支付", "98456231");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456231,完成", "98456231");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456232,创建", "98456232");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456232,支付", "98456232");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456232,完成", "98456232");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456233,创建", "98456233");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456233,支付", "98456233");
//        rocketMQTemplate.syncSendOrderly("rocketmq-test", "98456233,完成", "98456233");
//    }
//
//    /**
//     * 发送同步延迟消息
//     * 支持定时的延迟消息，但是不支持任意时间精度，仅支持特定的 level，例如定时 5s，
//     * 10s， 1m 等。其中，level=0 级表示不延时，level=1 表示 1 级延时，level=2 表示 2 级延时，以此类推。
//     * Level=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//     */
//    @ApiOperation("发送同步延时消息")
//    @RequestMapping(value = "/syncDelayMessage", method = RequestMethod.POST)
//    public ResponseData sendSyncDelayMessage(@RequestBody MqParam param) {
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//
//        producerHandler.sendSyncDelayMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    @ApiOperation("发送异步延时消息")
//    @RequestMapping(value = "/asyncDelayMessage", method = RequestMethod.POST)
//    public ResponseData sendAsyncDelayMessage(@RequestBody MqParam param) {
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//        producerHandler.sendAsyncDelayMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    /**
//     * 发送事务消息
//     */
//    @ApiOperation("发送事务消息")
//    @RequestMapping(value = "/transactionMessage", method = RequestMethod.POST)
//    public ResponseData sendTransactionMessage(@RequestBody MqParam param) {
//        // param
//        String topic = param.getTopic();
//        String tag = param.getTag();
//        Object payload = param.getPayload();
//        producerHandler.sendTransactionMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//
//}
