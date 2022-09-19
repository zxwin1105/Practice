package com.rabbit.producer.fanout;

import com.rabbit.producer.RabbitConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式-广播交换机方式
 * 交换机会将消息发送到每一个绑定的队列中去
 * @author zhaixinwei
 * @date 2022/9/16
 */
public class FanoutExchangeProducer {

    public static final String WEATHER_EXCHANGE_FANOUT = "weather_exchange_fanout";
    public void sendFanout() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        String message = "today weather: stream";
        channel.basicPublish(WEATHER_EXCHANGE_FANOUT,"",null,message.getBytes());

        channel.close();
        connection.close();
    }
}
