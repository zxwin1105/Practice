package com.rabbit.producer.workQueue;

import com.rabbit.producer.RabbitConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列模式 workQueue
 * 其特点是一个队列可以被多个消费者消费，不过消费的数据不会重复。
 * @author zhaixinwei
 * @date 2022/9/15
 */
@Slf4j
public class WorkQueueProducer {
    public static final String COLOR_EXCHANGE_ROUTING = "color_exchange_routing";

    public void sendWork() throws IOException, TimeoutException {
        Connection connection = RabbitConnection.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work_que",false,false,false,null);
        // 设置该值后mq不会给消费者将所有平均的消息给消费者，而是处理完一条 再取一条
//         channel.basicQos(1)

        for (int i = 0; i < 10000; i++) {
            String message = "work_mes:"+i;
            channel.basicPublish(""," ",null,("work_mes:"+i).getBytes());
            log.info("send suc message:{}",message );
        }

        channel.close();
        connection.close();
    }
}
