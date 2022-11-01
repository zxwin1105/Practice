package com.netty.cases.doubleway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 双向通信案例，客户端发送内容，服务端响应相同的内容
 *
 * @author zhaixinwei
 * @date 2022/11/1
 */
@Slf4j
public class DoubleWayServer {

    public static void main(String[] args) {
        ServerBootstrap server = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf msgBuf = (ByteBuf) msg;
                                log.debug("client:{} send msg:{}", ctx.channel().remoteAddress(), msgBuf.toString(Charset.defaultCharset()));
                                nioSocketChannel.writeAndFlush(msg);
                            }
                        });

                        pipeline.addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                ByteBuf msgBuf = (ByteBuf) msg;
                                log.debug("send msg:{}", msgBuf);
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                });
        server.bind(8079);

    }
}
