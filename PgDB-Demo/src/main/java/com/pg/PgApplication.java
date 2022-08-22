package com.pg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.pg.mapper")
public class PgApplication {
    public static void main(String[] args) {
        SpringApplication.run(PgApplication.class,args);
    }
}
