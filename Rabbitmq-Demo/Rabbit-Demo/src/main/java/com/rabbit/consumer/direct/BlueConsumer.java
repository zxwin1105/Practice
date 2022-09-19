package com.rabbit.consumer.direct;

import com.rabbit.consumer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaixinwei
 * @date 2022/9/16
 */
@Slf4j
public class BlueConsumer {

    public static final String COLOR_EXCHANGE_ROUTING = "color_exchange_routing";

    public void directQueue() throws IOException, TimeoutException {
        Connection rabbitConnection = RabbitConnection.getRabbitConnection();
        Channel channel = rabbitConnection.createChannel();
        final String queueName = "blue_que", routingKey = "blue";
//        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,COLOR_EXCHANGE_ROUTING,routingKey,null);
        channel.queueBind(queueName,COLOR_EXCHANGE_ROUTING,"pink",null);

        channel.basicConsume(queueName,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("routing queue:{},get message: {}",queueName,new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new BlueConsumer().directQueue();
    }
}
