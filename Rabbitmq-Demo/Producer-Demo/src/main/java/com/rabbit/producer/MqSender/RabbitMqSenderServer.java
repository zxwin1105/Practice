package com.rabbit.producer.MqSender;

import com.rabbit.producer.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * RabbitMq发送消息服务
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Service
public class RabbitMqSenderServer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqSenderServer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBootQueue(){
        rabbitTemplate.convertAndSend(RabbitMqConfig.BOOT_EXCHANGE_NAME,"d","Test sender");
    }
}
