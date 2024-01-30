//package com.orderway.system.bus.rocketmq.listener;
//
//import cn.hutool.core.util.StrUtil;
//import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
//import org.springframework.messaging.Message;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 定义本地事务处理类，实现 RocketMQLocalTransactionListener 接口，以及加上
// * RocketMQTransactionListener 注解，这个类 方法的调用是异步的；
// */
//@RocketMQTransactionListener
//public class TransactionListener implements RocketMQLocalTransactionListener {
//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
//        // 本地事务，可以执行业务逻辑 根据业务逻辑返回状态
//        // 状态有 bollback , commit or unknown 三种，分别是回滚事务，提交事务和未知；根据事务消息执行
//        // 获取到传过来的字符
//        String jsonString = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
//
//        if (StrUtil.equals("rocketmq1", jsonString)) {
//            return RocketMQLocalTransactionState.COMMIT;
//        } else if (StrUtil.equals("rocketmq2", jsonString)) {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
//        return RocketMQLocalTransactionState.UNKNOWN;
//    }
//
//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
//        // UNKNOWN 超时 或未收到应答 走这
//        String jsonString = new String((byte[]) message.getPayload());
//        System.out.println(jsonString + "======我到这里啦");
//        return RocketMQLocalTransactionState.COMMIT;
//    }
//}
