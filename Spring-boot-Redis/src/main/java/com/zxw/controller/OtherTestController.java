package com.zxw.controller;

import com.zxw.Res;
import com.zxw.ResEnum;
import com.zxw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    private int count = 0;

    @GetMapping("/test/suc")
    public Res<User> test(@RequestHeader String authorized,
                          Integer page, Integer size){
        if(authorized == null || "".equals(authorized)){
            return Res.failure(ResEnum.UNAUTHORIZED);
        }
        if(page == null || size == null ){
            return Res.failure(ResEnum.PARAMS_FAIL);
        }
        User user = new User();
        int code = count+1;
        user.setId(code);
        user.setName("suc"+code);
        user.setGender(code%2==0?"male":"female");

        return Res.success(user);
    }


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
