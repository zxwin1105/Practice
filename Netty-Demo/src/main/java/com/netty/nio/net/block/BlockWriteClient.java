package com.netty.nio.net.block;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zhaixinwei
 * @date 2022/10/26
 */
@Slf4j
public class BlockWriteClient {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(8077));
        long size = 0;
        // 读取服务端发送的消息

        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            size += channel.read(buffer);
            log.debug("读取数据：{}", size);
            buffer.clear();
        }
    }
}


