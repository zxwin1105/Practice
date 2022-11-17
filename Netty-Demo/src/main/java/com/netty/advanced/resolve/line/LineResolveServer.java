package com.netty.advanced.resolve.line;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 行解码器应用
 * - LineBasedFrameDecoder： 固定回车 '\r\n'为一行
 * - 自定义消息分隔符
 *
 * @author zhaixinwei
 * @date 2022/11/2
 */
@Slf4j
public class LineResolveServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // 添加行解码器
                        pipeline.addLast(new LineBasedFrameDecoder(1024));
//                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, ));
                        // 添加调试日志解析器
                        pipeline.addLast(new LoggingHandler());
                    }
                }).bind(8753);
    }
}
