package com.rocket.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>rocketmq 发送普通消息</p>
 * rocketmq支持三种发送消息的方式：同步发送、异步发送、单向传输
 * 同步发送和异步发送是可靠的
 *
 * @author zhaixinwei
 * @date 2022/10/9
 */
@Slf4j
public class NormalMessage {

    /**
     * 发送同步消息
     * 同步消息是指在发出一条消息后，会在收到服务端同步响应之后才发送下一条消息
     *
     * 同步发送方式请务必捕获发送异常，并做业务侧失败兜底逻辑，如果忽略异常则可能会导致消息未成功发送的情况。
     */
    public void sendSyncMessage() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        /*
         1. 创建一个producer，普通消息可以创建DefaultMQProducer,创建producer需要指定一个producerGroup。
         producerGroup 是指同一类producer的集合，同类的producer发送同一类消息且发送逻辑一致。
         */
        DefaultMQProducer producer = new DefaultMQProducer("normal_message_group");

        /*
         2. 设置nameserver地址，rocketMQ中设置nameserver的方式有多重，这里直接在producer中设置。多个地址用;隔开
         */
        producer.setNamesrvAddr("192.168.56.11:9876");
//        producer.setSendMsgTimeout(6000);
        // 启动producer
        producer.start();
        /*
         3. 构建消息体，指定Topic、tag、body等信息
         */
        for (int i = 0; i < 10; i++) {
            Message message = new Message("normal_message", "syncMessage", ("syncMessage"+i).getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息："+i);
            SendResult result = producer.send(message);
            System.out.printf("%s%n",result);
        }
        // 关闭producer
        producer.shutdown();
    }

    /**
     * 异步发送消息
     * 异步发送消息是指在发出一条消息后，不等待服务端响应，可以接着发送下一条消息
     * 异步发送需要实现异步发送回调接口（SendCallback）。
     */
    public void sendAsyncMessage() throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("normal_message_group");
        producer.setNamesrvAddr("192.168.56.11:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            final int messageIndex = i;
            Message message = new Message("normal_message","asyncMessage",("asyncMessage"+i).getBytes(StandardCharsets.UTF_8));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("消息发送成功：{}, 内容:{}", messageIndex,sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("消息发送失败：{}, 异常:{}", messageIndex,throwable.getCause());
                }
            });
        }
        TimeUnit.SECONDS.sleep(10);

        producer.shutdown();
    }

    /**
     * 发送单向消息
     * 发送方只负责发送消息，不等待服务端返回响应且没有回调函数触发，即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     */
    public void sendOnewayMessage() throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("normal_message_group");
        producer.setNamesrvAddr("192.168.56.11:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("normal_message", "oneway", ("oneway" + i).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(message);

        }
        producer.shutdown();

    }
}
