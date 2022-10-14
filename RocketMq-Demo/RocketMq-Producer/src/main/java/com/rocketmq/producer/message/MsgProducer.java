package com.rocketmq.producer.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 *
 * @author zhaixinwei
 * @date 2022/10/11
 */
@Slf4j
@Component
public class MsgProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    public void sendNormalMessage(){
        rocketMQTemplate.sendAndReceive("topic",null,MsgProducer.class);

    }

    public static void main(String[] args) {
        BigDecimal price = new BigDecimal("0");
        BigDecimal actualPrice = price.divide(new BigDecimal("2"), BigDecimal.ROUND_FLOOR);
        if (actualPrice.compareTo(new BigDecimal("0")) <= 0){
            actualPrice = new BigDecimal("0.01");
        }
        System.out.println(actualPrice.toString());
    }
}
