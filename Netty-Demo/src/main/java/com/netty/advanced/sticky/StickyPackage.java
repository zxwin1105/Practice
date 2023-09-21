package com.netty.advanced.sticky;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 黏包问题现象
 * @author zhaixinwei
 * @date 2022/11/1
 */
@Slf4j
public class StickyPackage {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 接收缓冲区为10
        serverBootstrap.option(ChannelOption.SO_RCVBUF, 15);
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // LoggingHandler用于日志监控
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                }).bind(8765);
    }
}
