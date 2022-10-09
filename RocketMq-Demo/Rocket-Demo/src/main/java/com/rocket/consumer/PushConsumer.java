package com.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * push消费者
 * Push是服务端主动推送消息给客户端，优点是及时性较好，但如果客户端没有做好流控，
 * 一旦服务端推送大量消息到客户端时，就会导致客户端消息堆积甚至崩溃。
 * @author zhaixinwei
 * @date 2022/10/9
 */
@Slf4j
public class PushConsumer {
    public static final String NORMAL_CONSUMER_GROUP = "normal_consumer_group";

    public static final String NORMAL_TOPIC = "normal_message";

    /**
     * 消费者消费 普通消息  Tag:oneway
     * @throws MQClientException
     */
    public void consumeNormal() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(NORMAL_CONSUMER_GROUP);
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


}
