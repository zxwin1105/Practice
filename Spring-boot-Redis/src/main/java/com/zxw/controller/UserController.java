package com.zxw.controller;

import com.zxw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.StringJoiner;

/**
 * @author: zhaixinwei
 * @date: 2022/5/12
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisTemplate<String, User> redisTemplate;


    @PostMapping("/add")
    public String add(@RequestBody User user){
        redisTemplate.opsForValue().set(genKey(user.getId()),user);
        return "success";
    }


    @DeleteMapping("/remove/{id}")
    public User remove(@PathVariable Integer id){
        return  redisTemplate.opsForValue().getAndDelete(genKey(id));
    }

    private String genKey(Integer id){
        StringJoiner joiner = new StringJoiner("");
        joiner.add("user:");
        joiner.add(String.valueOf(id));
        return joiner.toString();
    }
}
