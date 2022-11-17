package com.netty.advanced.resolve.fixlen;

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
 * 发送定长消息客户端
 *
 * @author zhaixinwei
 * @date 2022/11/2
 */
@Slf4j
public class FixLenResolveClient {

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
                                Random random = new Random();
                                ByteBuf buffer = ctx.alloc().buffer(100);
                                for (int i = 0; i < 10; i++) {
                                    byte[] data = fill10Bytes((char) i, random.nextInt(10) + 1);
                                    buffer.writeBytes(data);
                                }
                                // 将10次消息一次发出，观察服务端是否能正确解析消息
                                ctx.writeAndFlush(buffer);
                            }
                        });
                    }
                }).connect(new InetSocketAddress(8754));
        Channel channel = channelFuture.channel();

    }

    /**
     * 返回固定10个字节，填充len个字节为 character,其余为_
     *
     * @param character 字符
     * @param len       字符长度
     * @return
     */
    private static byte[] fill10Bytes(char character, int len) {
        byte[] res = new byte[10];
        for (int i = 0; i < 10; i++) {
            res[i] = (byte) (i >= len ? '-' : character);
        }
        return res;
    }
}

