package com.netty.cases.doubleway;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author zhaixinwei
 * @date 2022/11/1
 */
@Slf4j
public class DoubleWayClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
//                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug("send msg:{}",byteBuf);
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.info("server msg:{}", byteBuf.toString(Charset.defaultCharset()));
                                byteBuf.release();
                            }
                        });

                    }
                });
        ChannelFuture channelFuture = client.connect(new InetSocketAddress(8079));
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        log.debug("连接服务器成功");

        new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String next = scanner.next();
                if ("quit".equals(next)) {
                    channel.close();
                }
                channel.writeAndFlush(next);
            }
        }, "send-thread").start();

        channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                log.debug("开始关闭channel");
                eventLoopGroup.shutdownGracefully();
            }
        });

    }
}
