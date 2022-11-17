package com.netty.advanced.resolve.fixlen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 解决定长消息黏包半包问题
 * @author zhaixinwei
 * @date 2022/11/2
 */
@Slf4j
public class FixLenResolveServer {

    public static void main(String[] args) {

        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // 添加定长消息解析器，设置固定长度10
                        pipeline.addLast(new FixedLengthFrameDecoder(10));
                        // 添加调试日志解析器
                        pipeline.addLast(new LoggingHandler());
                    }
                }).bind(8754);
    }
}
