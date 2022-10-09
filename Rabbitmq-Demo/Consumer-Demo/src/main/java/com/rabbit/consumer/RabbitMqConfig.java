package com.rabbit.consumer;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaixinwei
 * @date 2022/9/22
 */
@Configuration
public class RabbitMqConfig {
    public static final String ERROR_EXCHANGE = "error_exchange";
    public static final String ERROR_QUEUE = "error_queue";

    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static final String TTL_EXCHANGE = "ttl_exchange";
    public static final String TTL_QUEUE = "ttl_queue";

    @Bean("errorExchange")
    public Exchange errorExchange(){
        return ExchangeBuilder.topicExchange(ERROR_EXCHANGE).durable(true).build();
    }

    @Bean("errorQueue")
    public Queue errorQueue(){
        return QueueBuilder.durable(ERROR_QUEUE)
                .build();
    }

    @Bean("errorBinding")
    public Binding errorBinding(@Qualifier("errorExchange") Exchange exchange,@Qualifier("errorQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("error.#").noargs();
    }

    @Bean("deadExchange")
    public Exchange deadExchange(){
        return ExchangeBuilder.topicExchange(DEAD_EXCHANGE).durable(true).build();
    }

    @Bean("deadQueue")
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE)
                .build();
    }

    @Bean("deadBinding")
    public Binding deadBinding(@Qualifier("deadExchange") Exchange exchange,@Qualifier("deadQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("dead.#").noargs();
    }

    @Bean("ttlExchange")
    public Exchange ttlExchange(){
        return ExchangeBuilder.topicExchange(TTL_EXCHANGE).durable(true).build();
    }

    @Bean("ttlQueue")
    public Queue ttlQueue(){
        return QueueBuilder.durable(TTL_QUEUE)
                .build();
    }

    @Bean("ttlBinding")
    public Binding ttlBinding(@Qualifier("ttlExchange") Exchange exchange,@Qualifier("ttlQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();
    }


    /**
     * retry机制耗尽次数后的处理策略
     * @return MessageRecoverer
     */
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate,ERROR_EXCHANGE,"error.retry.fail");
    }
}
