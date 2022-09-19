package com.rabbit.consumer.workQueue;

import com.rabbit.consumer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhaixinwei
 * @date 2022/9/15
 */
@Slf4j
public class WorkQueueConsumer {

    public void pullWork1() throws IOException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume("work_que",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consumer1:{}; 开始处理消息：{}",consumerTag,new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
    public void pullWork2() throws IOException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume("work_que",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consumer2:{}; 开始处理消息：{}",consumerTag,new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
    public void pullWork3() throws IOException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume("work_que",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consumer3:{}; 开始处理消息：{}",consumerTag,new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        WorkQueueConsumer workQueueConsumer = new WorkQueueConsumer();

        workQueueConsumer.pullWork1();
        workQueueConsumer.pullWork2();
        workQueueConsumer.pullWork3();
    }
}
