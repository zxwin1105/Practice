package com.zxw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaixinwei
 * @date 2022/7/26
 * @description
 */
@RestController
@RequestMapping("/other")
public class OtherTestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/set/number")
    public void setNumber(long number){
        final String key = "number";
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().increment(key);
        }else{
            redisTemplate.opsForValue().set(key,number);
        }
    }
}
