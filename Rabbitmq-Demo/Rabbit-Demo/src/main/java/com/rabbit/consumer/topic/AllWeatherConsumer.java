package com.rabbit.consumer.topic;

import com.rabbit.constant.WeatherRouting;
import com.rabbit.consumer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
public class AllWeatherConsumer {
    public void AllWeather() throws IOException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();

        String routingKey = "weather.#";
        String queueName = "weather_all";
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

    public static void main(String[] args) throws IOException {
        new AllWeatherConsumer().AllWeather();
    }
}
