package com.netty.nio.net.block;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.Net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 阻塞的服务端
 *
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class BlockServer {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8808));

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 服务监听，获取连接客户端的channel
            log.debug("等待客户端连接...");
            // accept 阻塞方法，线程停止运行。只到客户端连接到服务端
            SocketChannel socketChannel = ssc.accept();
            log.debug("客户端连接成功：{}",socketChannel);
            channels.add(socketChannel);
            for (SocketChannel channel : channels) {
                log.debug("等待客户端数据...");
                // 阻塞方法，线程停止运行，等待读入数据
                socketChannel.read(buffer);
                buffer.flip();
                log.debug("客户端数据：{}", StandardCharsets.UTF_8.decode(buffer).toString());
                buffer.clear();
            }
        }


    }
}
