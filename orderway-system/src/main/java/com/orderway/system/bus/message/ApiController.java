//package com.orderway.system.bus.message;
//
//import cn.hutool.core.lang.Dict;
//import cn.hutool.json.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.orderway.core.pojo.response.ResponseData;
//import com.orderway.core.pojo.response.SuccessResponseData;
//import com.orderway.system.bus.message.handler.MRocketHandler;
//import com.orderway.system.bus.rocketmq.param.MqParam;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
///**
// * 消息生产者
// */
//@RestController
//@Api(value = "API_消息中间件接口", tags = "API_消息中间件接口")
//@RequestMapping("/rocketmq")
//@Slf4j
//public class ApiController {
//
//    @Resource
//    private MRocketHandler mRocketHandler;
//
//    //创建主题
//    @PostMapping("/createTopic")
//    @ApiOperation("创建主题")
//    public ResponseData createTopic(@RequestBody Dict param) {
//        // param
//        String topic = param.getStr("topic");
//        String tag = param.getStr("tag");
//        //创建主题
//
//        return SuccessResponseData.success();
//    }
//
//
//    //同步发送
//    @PostMapping("/syncMessage")
//    @ApiOperation("发送同步消息")
//    public ResponseData sendSyncMessage(@RequestBody Dict param) {
//        // param
//        String topic = param.getStr("topic");
//        String tag = param.getStr("tag");
//        Object payload = param.getStr("payload");
//
//        mRocketHandler.sendSyncMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    //异步发送
//    @ApiOperation("发送异步消息")
//    @PostMapping("/asyncMessage")
//    public ResponseData sendAsyncMessage(@RequestBody Dict param) {
//        // param
//        String topic = param.getStr("topic");
//        String tag = param.getStr("tag");
//        Object payload = param.getStr("payload");
//
//        mRocketHandler.sendAsyncMessage(topic, tag, payload);
//        return SuccessResponseData.success();
//    }
//
//    //同步顺序
//    /**
//     * 发送同步顺序消息
//     * 因为broker会管理多个消息队列，hashKey的参数，主要就是用来计算选择队列的。
//     */
//    @ApiOperation("发送顺序消息")
//    @RequestMapping(value = "/orderlyMessage", method = RequestMethod.GET)
//    public void sendOrderlyMessage(@RequestBody Dict param) {
//        // param
//        String topic = param.getStr("topic");
//        String tag = param.getStr("tag");
//        String payload = param.getStr("payload");
//        List<Map> maps = JSONObject.parseArray(payload, Map.class);
//
//        mRocketHandler.sendOrderlyMessage(maps,topic,tag);
//    }
//
//}
