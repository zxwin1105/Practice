package com.redis.server;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis操作逻辑接口
 * @author zhaixinwei
 * @date 2022/7/1
 */
public interface RedisServer {

    void addVal(String key,Object val);

    /* 测试管道效率 */
    long addMul(int num);

    long addMulPipelined(int num);

    /* 模拟业务 */
    boolean buyBoot(int num);
}
