package com.rabbit.consumer.topic;

import com.rabbit.constant.WeatherRouting;
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
public class XianWeatherConsumer {

    public void xianWeather() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();

        String routingKey = "weather.xian.*";
        String queueName = "weather_xian";

        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName, WeatherRouting.WEATHER_EXCHANGE_ROUTING,routingKey);
        channel.basicConsume(queueName,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("xian weather get message:{}",new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new XianWeatherConsumer().xianWeather();
    }
}
