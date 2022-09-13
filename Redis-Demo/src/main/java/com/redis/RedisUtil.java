package com.redis;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author zhaixinwei
 * @date 2022/9/9
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /* String类型方法 */

    /**
     * 设置一个字符串类型的key-val
     * @param key 数据的key
     * @param val 数据本身
     */
    public void set(String key,Object val){
        redisTemplate.opsForValue().set(key,val);
    }

    public void setExpire(String key,Object val,long time){
        redisTemplate.opsForValue().set(key,val,time,TimeUnit.SECONDS);
    }
    /**
     * 根据Key获取对应的值
     * @param key key
     * @return object类型的数据
     */
    public Object getObj(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /* 类型共有方法 */


    /**
     * 给key设置过期时间，时间单位为s
     * @param key key
     * @param time 过期时间
     * @return 是否设置成功
     */
    public boolean expire(String key,long time){
        boolean res = Boolean.FALSE;
        if(time > 0){
            res = redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return res;
    }

    /* 管道操作 */
    public List<Object> pipelined(RedisCallback<?> callback){
        List<Object> objects = redisTemplate.executePipelined(callback);
        return objects;
    }

    public void descBy(String key, int i) {
        redisTemplate.opsForValue().decrement(key,i);
    }

    public int script(RedisScript<Integer> script, List<String> keys, Object... args){
        return redisTemplate.execute(script,keys,args);
    }
}
