package com.rabbit.consumer.mqlistener;

import com.rabbit.consumer.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * rabbitMq 消费者监听器内容
 *
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Service
public class RabbitMqListener {

    @RabbitListener(queues = "boot_queue", ackMode = "MANUAL")
    public void bootConsumer(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("boot message:{}",new String(message.getBody()));
        channel.basicReject(deliveryTag,false);
    }

//    long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        log.info("{}:consume message:{};properties:{}", deliveryTag, new String(message.getBody()), message.getMessageProperties().toString());
    // 模拟消息手动确认
//        if (deliveryTag % 2 == 0) {
//            /*
//             参数二: multiple，是否确认小于deliveryTag的所有消息，false只确认当前消息
//             参数三: requeue，拒绝签收后，消息是否重新回到queue排队
//             */
//        channel.basicNack(deliveryTag, false, true);
//    } else {
//        channel.basicAck(deliveryTag, true);
//    }
    @RabbitListener(queues = "retry_queue")
    public void retryConsumer(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("retry_queue:{}", new String(message.getBody()));
        int i = 1 / 0;
    }

    @RabbitListener(queues = RabbitMqConfig.ERROR_QUEUE)
    public void errorConsumer(Message message){
        log.info("retry failed message ： {}",new String(message.getBody()));
    }


    @RabbitListener(queues = RabbitMqConfig.TTL_QUEUE)
    public void delayConsumer(Message message){
        log.info("delay message 关闭订单：{}",new String(message.getBody()));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "delayQueue",durable = "true"),
            exchange = @Exchange(value = "delayExchange",delayed = "true"),
            key = "delay.plugins"
    ))
    public void delayPlugins(Message message){
        log.info("delay plugins message:{}",new String(message.getBody()));
    }
}
