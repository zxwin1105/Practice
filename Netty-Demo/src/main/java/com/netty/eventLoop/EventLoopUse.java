package com.netty.eventLoop;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Netty中EventLoop 和 EventLoopGroup的关系和使用
 * EventLoop:是事件循环对象。是一个单线程执行器，可以执行普通任务、定时任务。还用于处理Channel上的IO事件。(EventLoop只有一个线程)
 * EventLoopGroup:事件循环组，是一组EventLoop。Channel一般需要调用EventLoopGroup的register()方法来绑定一个EventLoop。
 *
 * @author zhaixinwei
 * @date 2022/10/27
 */
@Slf4j
public class EventLoopUse {
    public static void main(String[] args) {
        /*
            EventLoopGroup接口有多个实现，常用的有：
            - NioEventLoopGroup:可以执行普通任务、定时任务、IO事件
            - DefaultEventLoopGroup:可以执行普通任务、定时任务
         */
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        // 调用EventLoopGroup#next() 可以获取group中一个EventLoop
        EventLoop next = eventLoopGroup.next();

        // 执行普通任务
        next.execute(()->{
            log.debug("eventLoop execute normal task");
        });

        // 执行定时任务，重新从group中获取一个EventLoop
        eventLoopGroup.next().scheduleAtFixedRate(()->{
            log.debug("eventLop execute schedule task");
        },0,100, TimeUnit.MILLISECONDS);
    }

}
