package com.netty.future;

import com.netty.eventLoop.EventLoopUse;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * NettyPromise的使用
 * Promise初始化需要传递一个Execute，可以自己给Promise设置成功返回值，或者失败返回值
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class NettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();

        DefaultPromise<String> promise = new DefaultPromise<>(eventLoop);

        new Thread(()->{
            log.debug("thread task start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
            log.debug("thread task completed");
            promise.setSuccess("success");
        }).start();

        String result = promise.get();
        log.info("result: {}",result);
    }
}
