package com.netty.advanced.resolve.length;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过传输消息长度，解决消息黏包问题。
 *
 * @author zhaixinwei
 * @date 2022/11/2
 */
@Slf4j
public class LengthFieldResolve {

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
                new LoggingHandler()
        );


        // 发送消息到channel
        ByteBuf buf = channel.alloc().buffer(1024);
        writeLenAndMsg(buf, "hello, world");
        writeLenAndMsg(buf, "hello, netty");
        writeLenAndMsg(buf, "hello, java");
        writeLenAndMsg(buf, "hello, CloudNative");

        channel.writeInbound(buf);
    }

    private static void writeLenAndMsg(ByteBuf buf, String msg) {
        byte[] bytes = msg.getBytes();
        // 写入长度
        buf.writeInt(bytes.length);
        buf.writeBytes(msg.getBytes());
    }
}
