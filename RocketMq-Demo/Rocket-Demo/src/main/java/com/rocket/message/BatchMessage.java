package com.rocket.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * RocketMq 批量消息发送
 * 在一些要求吞吐率的业务场景中，可以将一些消息汇聚成一批后进行发送，减少API和网路调用次数
 *
 * 注意在批量发送消息时，批量消息的大小不能超过 1MiB；同一批消息的topic必须相同
 * @author zhaixinwei
 * @date 2022/10/10
 */
@Slf4j
public class BatchMessage {

    public static final String BATCH_TOPIC = "BATCH_TOPIC";
    private static final String PRODUCER_BATCH_GROUP = "PRODUCER_BATCH_GROUP";

    /**
     * 发送批量消息
     */
    public void sendBatchMessage() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_BATCH_GROUP);
        producer.setNamesrvAddr("192.168.56.11:9876");
        producer.start();
        List<Message> messageList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Message message = new Message(BATCH_TOPIC,"batch",("batch_message"+i).getBytes(StandardCharsets.UTF_8));
            messageList.add(message);
        }
        producer.send(messageList);
        log.info("发送批量消息：{}",messageList);
        producer.shutdown();
    }

}
