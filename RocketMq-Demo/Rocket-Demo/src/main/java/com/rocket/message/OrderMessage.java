package com.rocket.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * 顺序消息：顺序消息是一种对消息发送和消费顺序有严格要求的消息
 * 在RocketMQ中支持分区顺序消息，需要在发送消息时指定shardingKey，
 * mq会将同一shardingKey的消息发送到同一队列中，并按顺序进行消费。
 *
 * 需要注意：rocketmq消息的顺序性分为生成顺序性和消费顺序性，只有同时满足才能保证消息顺序性
 * - 生产顺序性保证：单一生产者；串行发送
 * @author zhaixinwei
 * @date 2022/10/9
 */
@Slf4j
public class OrderMessage {

    public static final String ORDER_TOPIC = "ORDER_TOPIC";
    private static final String PRODUCER_ORDER_GROUP = "PRODUCER_ORDER_GROUP";
    public static final String[] TAGS = {"ORDER_TAG","PAYMENT_TAG","SHIPPING_TAG"};


    /**
     * 发送顺序消息
     * 需要提供shardingKey保证将相同的shardingKey分配到同一队列,
     * mq会保证同一队列中相同shardingKey消息的顺序性
     *
     * eg: 案例中需要保证同一订单生成、付款和发货顺序执行。以orderId作为shardingKey将同一订单消息分配到同一队列
     */
    public void sendOrderMessage() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_ORDER_GROUP);
        producer.setNamesrvAddr("192.168.56.11:9876");
        producer.start();
        // 自定义业务Tag
        for (int i = 0; i < 9; i++) {
            String tag = TAGS[i % TAGS.length];
            // 订单号
            int orderId = 2022000+(i/3);
            log.info("发送消息，orderId:{}，tag:{}",orderId,tag);
            // 定义消息
            Message message = new Message(ORDER_TOPIC,tag,String.valueOf(orderId).getBytes(StandardCharsets.UTF_8));

            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    // 定义shardingKey，选择队列
                    int orderId = (int)o;
                    log.info("队列选择器，选择结果：{}",orderId % list.size());
                    return list.get(orderId % list.size());
                }
            },orderId);
        }
        producer.shutdown();
    }

}
