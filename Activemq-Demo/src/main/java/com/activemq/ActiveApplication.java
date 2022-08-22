package com.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author: zhaixinwei
 * @date: 2022/6/29
 * @description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJms
public class ActiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveApplication.class,args);
    }
}
