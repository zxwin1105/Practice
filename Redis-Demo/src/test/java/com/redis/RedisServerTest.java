package com.redis;

import com.redis.server.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

/**
 * @author zhaixinwei
 * @date 2022/9/9
 */
@SpringBootTest(classes = RedisApplication.class)
public class RedisServerTest {

    private final RedisServer redisServer;

    @Autowired
    public RedisServerTest(RedisServer redisServer){
        this.redisServer = redisServer;
    }

    @Test
    public void addVal_normal_test(){
        redisServer.addVal("test:str:normal","test normal");
    }

    @Test
    public void addVal_null_test(){
        redisServer.addVal("test:str:null",null);
    }

    @Test
    public void addVal_num_test() {
        redisServer.addVal("test:str:num",1);
    }

    @Test
    public void pipeline_test(){
        long non_pipeline = redisServer.addMul(100);
        long pipeline = redisServer.addMulPipelined(100);
        System.out.println(non_pipeline);
        System.out.println(pipeline);
    }


    @Test
    public void bis_test(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    redisServer.buyBoot(random.nextInt(3)+1);
                }
            }).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
