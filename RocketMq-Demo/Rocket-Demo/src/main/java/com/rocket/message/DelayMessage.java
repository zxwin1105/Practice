package com.rocket.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 延迟消息发送
 * 延迟消息发送是指消息发送到Apache RocketMQ后，并不期望立马投递这条消息，而是延迟一定时间后才投递到Consumer进行消费。
 * rocketMQ默认只支持18个等级的延迟投递1s、5s、10s、30s、1m、2m、3m、4m、5m、6m、7m、8m、9m、10m、20m、30m、1h、2h
 * @author zhaixinwei
 * @date 2022/10/10
 */
@Slf4j
public class DelayMessage {

    public static final String DELAY_TOPIC = "DELAY_TOPIC";
    public static final String PRODUCER_DELAY_GROUP = "PRODUCER_DELAY_GROUP";

    /**
     * 发送延迟消息
     */
    public void sendDelayMessage() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_DELAY_GROUP);
        producer.setNamesrvAddr("192.168.56.11:9876");
        producer.start();
        // 构建消息
        Message message = new Message(DELAY_TOPIC,"delay_message".getBytes(RemotingHelper.DEFAULT_CHARSET));
        message.setDelayTimeLevel(3);
        SendResult send = producer.send(message);
        log.info("发送延时消息：{}",send);
        producer.shutdown();
    }
}
