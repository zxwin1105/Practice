package com;

import com.pay.config.MyWXPayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: zhaixinwei
 * @date: 2022/6/19
 * @description:
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }
}
