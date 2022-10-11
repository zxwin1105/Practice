package com.rocket.consumer;

import com.rocket.message.BatchMessage;
import com.rocket.message.DelayMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * push消费者
 * Push是服务端主动推送消息给客户端，优点是及时性较好，但如果客户端没有做好流控，
 * 一旦服务端推送大量消息到客户端时，就会导致客户端消息堆积甚至崩溃。
 * @author zhaixinwei
 * @date 2022/10/9
 */
@Slf4j
public class PushConsumer {
    public static final String CONSUMER_NORMAL_GROUP = "CONSUMER_NORMAL_GROUP";
    public static final String CONSUMER_DELAY_GROUP = "CONSUMER_DELAY_GROUP";
    public static final String CONSUMER_BATCH_GROUP = "CONSUMER_BATCH_GROUP";

    public static final String NORMAL_TOPIC = "normal_message";

    /**
     * 消费者消费 普通消息  Tag:oneway
     * @throws MQClientException
     */
    public void consumeNormal() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_NORMAL_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        // 设置消费者方式为集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 订阅一个或多个Topic，可以通过Tag过滤Message，可以使用通配符 * 表示接收所有的消息，订阅多个Tag可以使用||分隔Tag
        consumer.subscribe(NORMAL_TOPIC, "oneway");

        // 注册回调接口处理broker中的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            log.info("{} 处理消息：{}", Thread.currentThread().getName(),list);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }


    /**
     * 消费延时消息
     */
    public void consumerDelayMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_DELAY_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        // 设置消费者方式为集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(DelayMessage.DELAY_TOPIC,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                log.info("消费延时消息：{}",list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }


    /**
     * 消费批量消息
     */
    public void consumerBatchMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_BATCH_GROUP);
        consumer.setNamesrvAddr("192.168.56.11:9876");
        // 设置消费者方式为集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(BatchMessage.BATCH_TOPIC,"batch");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("消费批量消息：{}",msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

}
