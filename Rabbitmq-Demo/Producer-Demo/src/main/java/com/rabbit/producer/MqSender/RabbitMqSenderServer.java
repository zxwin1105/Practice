package com.rabbit.producer.MqSender;

import com.rabbit.producer.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * RabbitMq发送消息服务
 *
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Service
public class RabbitMqSenderServer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqSenderServer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBootQueue(int i) {
        // 如果配置为false 将不会触发return确认机制
//        rabbitTemplate.setMandatory(false);
        rabbitTemplate.convertAndSend(RabbitMqConfig.BOOT_EXCHANGE_NAME, "boot.queue", "Test sender" + i);
    }


    public void sendRetry() {
        rabbitTemplate.convertAndSend(RabbitMqConfig.BOOT_EXCHANGE_NAME, "retry.test", "retry message");
    }

    public void sendAndConfirm() {
        CorrelationData correlationData = new CorrelationData();
        // 设置消息的唯一ID
        correlationData.setId("confirm-1");
        correlationData.getFuture().addCallback(result -> {
            if (result != null && result.isAck()) {
                log.info("message push success:{}", correlationData.getId());
            } else {
                log.error("message push failed:{}", correlationData.getId());
            }
        }, ex -> {
            log.error("exception");
        });

        Message message = MessageBuilder.withBody("durable".getBytes())
                // 设置消息持久化
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        rabbitTemplate.convertAndSend(RabbitMqConfig.BOOT_EXCHANGE_NAME, "boot.confirm", message, correlationData);
    }

    public void sendDeadLetter(){
        Message message = MessageBuilder.withBody("dead letter".getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();

        rabbitTemplate.convertAndSend(RabbitMqConfig.BOOT_EXCHANGE_NAME,"boot.dead",message);
    }


    public void sendOrder(){
        Message message = MessageBuilder.withBody("order-1".getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_EXCHANGE,"order.o1",message);
    }

    public void sendDelay(){
        Message message = MessageBuilder.withBody("delay-message".getBytes())
                .setHeader("x-delay",10000L) // 消息头设置延时
                .build();
        rabbitTemplate.convertAndSend("delayExchange","delay.plugins",message);
    }
}
