package com.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试pipeline和handler的使用
 * pipeline 相当于一个数据管道，handler则相当于数据管道中的一个个数据处理器。
 * netty中handler主要有两种:
 * - 入站：处理数据流入
 * - 出站：处理数据流出
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class PipelineUse {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        /*
                            给Pipeline添加多个 入站 处理器。每个入站处理器中监听执行read事件
                         */
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("client:{} send msg:{}",ctx.channel().remoteAddress(),msg);
                                nioSocketChannel.writeAndFlush("success received msg");
                                super.channelRead(ctx, msg);
                            }
                        });


                        // 为pipeline添加 出站 处理器
                        pipeline.addLast(new StringEncoder());

                        pipeline.addLast("h2", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("server send msg:{}",msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(8979);

    }
}
