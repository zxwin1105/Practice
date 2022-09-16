package com.redis.server.impl;

import com.redis.RedisUtil;
import com.redis.server.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author zhaixinwei
 * @date 2022/9/9
 */
@Slf4j
@Service
public class RedisServerImpl implements RedisServer {

    private final RedisUtil redisUtil;

    public RedisServerImpl(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    @Override
    public void addVal(String key,Object val) {
        redisUtil.set(key,val);
    }

    /**
     * 不使用管道技术添加多个k-v
     * @param num k-v数量
     * @return 用时
     */
    @Override
    public long addMul(int num) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            redisUtil.setExpire("no_pipe:"+i,i,100);
        }

        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 使用管道技术添加多个k-v
     * @param num k-v数量
     * @return 用时
     */
    @Override
    public long addMulPipelined(int num) {
        long start = System.currentTimeMillis();
        redisUtil.pipelined(connection -> {
            for (int i = 0; i < num; i++) {
                connection.setEx(("pipe"+i).getBytes(),100,(i+"").getBytes());
            }
            return null;
        });
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 购买靴子业务，从redis缓存中扣除库存，如果扣除成功，购买成功，每人最多买两双
     * 该功能可能有多个实例。会出现问题，肯能会将库存卖成负数，因为get和desc是两个操作
     * 不具备原子性，在这两个操作之间肯能会有其他请求扣除库存。
     * 可以使用Lua脚本来实现，Redis会保证Lua脚本的原子性
     * @param num 购买多少双
     * @return
     */
    @Override
    public boolean buyBoot(int num) {
        final String key = "good:boot:10021";
//        log.info(Thread.currentThread().getName()+"buy {}",num);
//        if(num < 0 || num > 2){
//            log.info(Thread.currentThread().getName()+"buy fail");
//            return false;
//        }
//        int bootNum = Integer.parseInt(String.valueOf(redisUtil.getObj(key)));
//        if(bootNum >= num){
//            redisUtil.descBy(key,2);
//        }else{
//            log.info(Thread.currentThread().getName()+"buy fail");
//            return false;
//        }
//        log.info(Thread.currentThread().getName()+"buy suc");
//        return true;
        // lua 脚本方式
        String script = "local isExists = redis.call('exists',KEYS[1]) " +
                "if isExists == 0 then " +
                "return 0 " +
                "end " +
                "local num = tonumber(redis.call('get',KEYS[1]))" +
                "local res = 0 " +
                "if (num - tonumber(ARGV[1])) >= 0 then " +
                "redis.call('decrBy',KEYS[1],tonumber(ARGV[1]))" +
                "end " +
                "return res ";
        int res = redisUtil.script(new RedisScript<Integer>() {
            @Override
            public String getSha1() {
                return "10021";
            }

            @Override
            public Class<Integer> getResultType() {
                return Integer.class;
            }

            @Override
            public String getScriptAsString() {
                return script;
            }
        }, Lists.list(key), String.valueOf(num));
        return res == 0;
    }
}
