package com.rabbit.producer.config;

import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * RabbitMq配置类，并配置消息确认
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Configuration
public class RabbitMqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    public static final String BOOT_EXCHANGE_NAME = "boot_exchange";

    public static final String BOOT_QUEUE = "boot_queue";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqConfig(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    /**
     * 声明交换机
     * @return exchange
     */
    @Bean("bootExchange")
    public Exchange bootExchange(){
        // durable:是否持久化
        return ExchangeBuilder.topicExchange(BOOT_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明队列
     * @return queue
     */
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(BOOT_QUEUE).build();
    }

    /**
     * 绑定交换机和队列
     * @param exchange 交换机
     * @param queue 队列
     * @return binding
     */
    @Bean("bindBoot")
    public Binding bindBoot(@Qualifier("bootExchange") Exchange exchange,@Qualifier("bootQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b){
            log.info("消息成功投递到exchange");
        }else {
            log.error("消息投递exchange失败");
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息投递queue失败，返回消息{}", new String(returnedMessage.getMessage().getBody()));

    }
}
