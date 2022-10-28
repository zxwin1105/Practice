package com.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用netty实现hello world网络服务端
 *
 * @author zhaixinwei
 * @date 2022/10/27
 */
@Slf4j
public class HelloServer {

    public static void main(String[] args) {
        // 1. 服务端启动类
        new ServerBootstrap()
                // 2. 添加eventLoop。第一个group处理accept事件；第二个服负责socketChannel读写事件
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                // 3. 选择channel实现
                .channel(NioServerSocketChannel.class)
                // 4. 添加处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("msg:{}", msg);
                            }
                        });
                    }
                }).bind(8899);
    }
}
