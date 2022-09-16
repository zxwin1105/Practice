package com.rabbit.producer.direct;

import com.rabbit.producer.RabbitConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式-routing交换机模式
 * 将消息发布到exchange需要指定一个routing，创建的队列也需要绑定一个routing
 * exchange会根据routing将消息发布到对应routing的队列中
 * @author zhaixinwei
 * @date 2022/9/16
 */
@Slf4j
public class DirectExchangeProducer {

    public static final String COLOR_EXCHANGE_ROUTING = "color_exchange_routing";

    public static final Random random = new Random();
    public void sendDirect() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        String[] colors = {"yellow","pink","blue"};
        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(3);
            String message = "color is " + colors[index] + ":"+i;
            channel.basicPublish(COLOR_EXCHANGE_ROUTING,colors[index],null,message.getBytes());
        }
        channel.close();
        connection.close();
    }
}
