package com.netty.pipeline;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * EmbeddedChannel 是Netty提供的 可供测试handler的工具。
 * 不需要启动服务端客户端就可以进行测试。
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class EmbeddedChannelUse {

    public static void main(String[] args) {
        // 定义handler
        ChannelInboundHandlerAdapter in_h1 = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("in_h1");
                super.channelRead(ctx, msg);
            }
        };
        ChannelInboundHandlerAdapter in_h2 = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("in_h2");
                super.channelRead(ctx, msg);
            }
        };
        ChannelOutboundHandlerAdapter out_h1 = new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("out_h1");
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter out_h2 = new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("out_h2");
                super.write(ctx, msg, promise);
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(in_h1,in_h2,out_h1,out_h2);
        // 模拟入站操作
//        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("in_msg".getBytes()));

        channel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("out_msg".getBytes()));
    }
}
