package com.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author zhaixinwei
 * @date 2022/10/27
 */
@Slf4j
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // 添加多个handler
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("server msg:{}", msg);
                                super.channelRead(ctx, msg);
                            }
                        });

                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("write msg:{}", msg);
                                super.write(ctx, msg, promise);
                            }
                        });

                    }
                })
                // #connect() 是一个异步方法，真正执行#connect()方法的是nio线程
                .connect(new InetSocketAddress(8979));
        // 所以要通过ChannelFuture获取channel对象，进行操作，必须等待nio执行完成
        // 方法一：同步阻塞
        channelFuture.sync();
        Channel channel = channelFuture.channel();

        // 方法二：异步
//        channelFuture.addListener(new ChannelFutureListener() {
//            // nio建立连接成功后，会调用operationComplete方法
//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                log.debug("connected");
//                channelFuture.channel().writeAndFlush("异步");
//            }
//        });

        // 正确关闭客户端
        new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String next = scanner.next();
                if (next.equals("quit")) {
                    // 异步方法
                    channel.close();
                }
                channel.writeAndFlush(next);
            }
        },"send").start();

        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("client closed");
                // group停止接收事件任务，并关闭所有线程
                group.shutdownGracefully();
            }
        });
    }
}
