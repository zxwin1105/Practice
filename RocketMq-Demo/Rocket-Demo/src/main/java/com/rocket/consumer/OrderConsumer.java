package com.rocket.consumer;

import com.rocket.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author zhaixinwei
 * @date 2022/10/9
 */
@Slf4j
public class OrderConsumer {
    private static final String CONSUMER_ORDER_GROUP = "CONSUMER_ORDER_GROUP";

    /**
     * 消费订单消息
     */
    public void consumerOrder() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_ORDER_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(OrderMessage.ORDER_TOPIC,OrderMessage.TAGS[0]);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                log.info("消费订单消息：{}",list);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }

    /**
     * 消费订单支付消息
     */
    public void consumerPayment() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_ORDER_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(OrderMessage.ORDER_TOPIC,OrderMessage.TAGS[1]);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                log.info("消费订单支付消息：{}",list);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }

    /**
     * 消费发货消息
     */
    public void consumerShipping() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_ORDER_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(OrderMessage.ORDER_TOPIC,OrderMessage.TAGS[2]);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                log.info("消费发货消息：{}",list);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }
}
