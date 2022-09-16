package com.rabbit.consumer.basicQueue;

import com.rabbit.consumer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 基本消息队列消费者
 *
 * @author zhaixinwei
 * @date 2022/9/15
 */

@Slf4j
public class BasicQueueConsumer {


    public void pullBasic() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();

        // 消费消息，false手动确认 ack
        channel.basicConsume("basic_que", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                log.info("接收到消息，开始处理----------");
                log.info("消息内容：{};消息编号：{}", new String(body), envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(), false);
                log.info("ack suc");
            }
        });
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        BasicQueueConsumer basicQueueConsumer = new BasicQueueConsumer();
        basicQueueConsumer.pullBasic();
    }
}
