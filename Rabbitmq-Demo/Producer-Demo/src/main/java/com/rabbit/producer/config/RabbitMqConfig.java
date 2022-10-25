package com.rabbit.producer.config;

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
 *
 * @author zhaixinwei
 * @date 2022/9/19
 */
@Slf4j
@Configuration
public class RabbitMqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    public static final String BOOT_EXCHANGE_NAME = "boot_exchange";

    public static final String BOOT_QUEUE = "boot_queue";
    public static final String RETRY_QUEUE = "retry_queue";
    public static final String ORDER_EXCHANGE= "order_exchange";
    public static final String ORDER_QUEUE = "order_queue";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 声明交换机
     *
     * @return exchange
     */
    @Bean("bootExchange")
    public Exchange bootExchange() {
        // durable:是否持久化
        return ExchangeBuilder.topicExchange(BOOT_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明队列
     *
     * @return queue
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(BOOT_QUEUE)
                .deadLetterExchange("dead_exchange")
                .deadLetterRoutingKey("dead.boot")
                .build();
    }

    @Bean("retryQueue")
    public Queue retryQueue(){
        return QueueBuilder.durable(RETRY_QUEUE)
                .build();
    }

    @Bean("orderExchange")
    public Exchange orderExchange(){
        return ExchangeBuilder.topicExchange(ORDER_EXCHANGE).durable(true).build();
    }

    @Bean("orderQueue")
    public Queue orderQueue(){

        return QueueBuilder.durable(ORDER_QUEUE)
                // 设置队列为ttl 单位ms
                .ttl(5000)
                .deadLetterExchange("ttl_exchange")
                .deadLetterRoutingKey("ttl.order")
                .build();
    }


    @Bean("orderBinding")
    public Binding orderBinding(@Qualifier("orderExchange") Exchange exchange,@Qualifier("orderQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
    }

    /**
     * 绑定交换机和队列
     *
     * @param exchange 交换机
     * @param queue    队列
     * @return binding
     */
    @Bean("bindBoot")
    public Binding bindBoot(@Qualifier("bootExchange") Exchange exchange, @Qualifier("bootQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @Bean("bindRetry")
    public Binding bindRetry(@Qualifier("bootExchange")Exchange exchange,@Qualifier("retryQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("retry.#").noargs();
    }



    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            log.info("global消息成功投递到exchange:{}", s);
        } else {
            log.error("global消息投递exchange失败:{}", s);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息投递queue失败，返回消息{}", new String(returnedMessage.getMessage().getBody()));
    }
}
