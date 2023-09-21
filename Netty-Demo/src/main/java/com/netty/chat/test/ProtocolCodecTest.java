package com.netty.chat.test;

import com.netty.chat.protocol.ProtocolCodec;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 协议解析器测试
 * @author seisei
 * @date 2023/9/16
 */
@ChannelHandler.Sharable
public class ProtocolCodecTest {

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new ProtocolCodec()
        );

        // 测试输出内容
//        ChatRespMessage chatRespMessage = new ChatRespMessage();
//        channel.writeOutbound(chatRespMessage);



    }
}
