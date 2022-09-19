package com.rabbit.producer.topic;

import com.rabbit.constant.WeatherRouting;
import com.rabbit.producer.RabbitConnection;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式-topic
 *
 * @author zhaixinwei
 * @date 2022/9/16
 */
@Slf4j
public class TopicExchangeProducer {

    public void topic() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        // 监听器
        // confirm确认，确保消息发送到了exchange
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                log.info("confirm: 消息{}成功发送到exchange", deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                log.error("confirm: 消息{}成功发送到exchange", deliveryTag);
            }
        });

        // return确认, 确保消息发送到了queue
        channel.addReturnListener(returnMessage -> log.error("send message {} to queue failed,routingKey {}", new String(returnMessage.getBody()), returnMessage.getRoutingKey()));
        // 获取所有的WeatherRouting
        Map<String, String> weatherMap = WeatherRouting.WEATHER_MAP;
        Set<Map.Entry<String, String>> entries = weatherMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String message = entry.getKey() + entry.getValue();
            log.info("send:{}", entry.getKey() + ":" + entry.getValue());
            channel.basicPublish(WeatherRouting.WEATHER_EXCHANGE_ROUTING, entry.getKey(), true, null, message.getBytes());
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new TopicExchangeProducer().topic();
    }
}
