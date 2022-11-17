package com.netty.advanced.resolve.line;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @author zhaixinwei
 * @date 2022/11/2
 */
@Slf4j
public class LineResolveClient {
    public static void main(String[] args) {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // 添加日志处理handler
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer(1024);
                                for (int i = 0; i < 10; i++) {
                                    byte[] data = makeLineMsg('\n');
                                    buffer.writeBytes(data);
                                }
                                // 将10次消息一次发出，观察服务端是否能正确解析消息
                                ctx.writeAndFlush(buffer);
                            }
                        });
                    }
                }).connect(new InetSocketAddress(8753));
        Channel channel = channelFuture.channel();

    }

    private static byte[] makeLineMsg(char character) {
        Random random = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < random.nextInt(100) + 1; i++) {
            res.append((char) (random.nextInt(98)+32));
        }
        res.append(character);
        log.debug("msg:{}",res.toString());
        return res.toString().getBytes();
    }
}
