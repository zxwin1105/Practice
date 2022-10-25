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
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8808));
        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello server");
        socketChannel.write(buffer);
        System.out.println("d");
    }
}
