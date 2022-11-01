package com.netty.advanced.sticky;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author zhaixinwei
 * @date 2022/11/1
 */
@Slf4j
public class StickyClient {

    public static void main(String[] args) {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            // 连接建立后，会触发active事件
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 为测试黏包问题，连续发10次数据到服务端，观察服务端接收数据的次数，大小
                                for (int i = 0; i < 10; i++) {
                                    ByteBuf buffer = ctx.alloc().buffer(16);
                                    buffer.writeBytes(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                                    ctx.writeAndFlush(buffer);
                                }
                            }
                        });
                    }
                }).connect(new InetSocketAddress(8765));
    }
}
