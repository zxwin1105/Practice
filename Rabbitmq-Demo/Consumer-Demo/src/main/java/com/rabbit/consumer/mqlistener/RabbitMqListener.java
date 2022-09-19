package com.rabbit.consumer.mqlistener;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Service;

/**
 * rabbitMq 消费者监听器内容
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Service
public class RabbitMqListener {

    @RabbitListener(queues = "boot_queue")
    public void bootConsumer(Message message){

        log.info("consume message:{};properties:{}",new String(message.getBody()),message.getMessageProperties().toString());
    }
}
