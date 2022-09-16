package com.rabbit.producer.basicQueue;

import com.rabbit.producer.RabbitConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 使用smqp实现rabbitmq 基本消息队列 的消息发送
 * 基本消息队列特点是 只有一个生产者和一个消费者它们绑定同一个队列
 * @author zhaixinwei
 * @date 2022/9/15
 */
@Slf4j
public class BasicQueueProducer {

    public void sendBasic() throws IOException, TimeoutException {
        // 获取rabbit连接
        Connection connection = RabbitConnection.getRabbitConnection();
        // 创建虚拟连接
        Channel channel = connection.createChannel();
        /*
        参数一：队列名称
        参数二：持久化
        参数三：是否队列私有化，false代表所有消费者都可以访问，true代表只有第一个消费者才能一直访问
        参数四：是否自动删除，false代表连接停掉不会删除队列
         */
        channel.queueDeclare("basic_que",false,false,false,null);
        String message = "basic message";
        channel.basicPublish("","basic_que",null,message.getBytes());
        log.info("basic消息发送成功");
        channel.close();
        connection.close();
    }
}
