package com.netty.nio.net.block;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class BlockClient {

    public static void main(String[] args) throws IOException {
        // 在调用客户端时，不能让客户端运行结束关闭，需要打断点或其他方式让客户端不能结束
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8678));
        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello server");
        socketChannel.write(buffer);
        socketChannel.close();
    }
}
