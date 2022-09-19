package com.rabbit.consumer.fanout;

import com.rabbit.consumer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhaixinwei
 * @date 2022/9/16
 */
@Slf4j
public class BaiduWeatherConsumer {
    public static final String WEATHER_EXCHANGE_FANOUT = "weather_exchange_fanout";

    public void fanoutConsumer() throws IOException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("baidu_weather",false,false,false,null);
        // 队列绑定交换机
        channel.queueBind("baidu_weather",WEATHER_EXCHANGE_FANOUT,"");
        channel.basicConsume("baidu_weather",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("baidu get weather info:{}",new String(body));
                // 确认消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new BaiduWeatherConsumer().fanoutConsumer();
    }
}
