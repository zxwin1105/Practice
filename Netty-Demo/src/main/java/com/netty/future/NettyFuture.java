package com.netty.future;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class NettyFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = eventLoopGroup.next();

        Future<String> future = eventLoop.submit(() -> {
            log.debug("begin execute task");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("execute thread task");
            return "task completed";
        });
        // 同步方式获取异步结果
        String syncResult = future.get();
        log.info("sync get result:{}", syncResult);

        // 异步方式获取执行结果
        Future<String> stringFuture = future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                log.info("async get result:{}", future.get());
            }
        });
        /*
        对比通过获取结果和异步获取结果的时间，可以发现同步获取结果的效率可能回更能高。
        使用异步主要时为了提高吞吐量。
         */

    }
}
