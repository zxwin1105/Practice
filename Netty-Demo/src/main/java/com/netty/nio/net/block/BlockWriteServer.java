package com.netty.nio.net.block;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 服务端向客户端写入大量内容
 *
 * @author zhaixinwei
 * @date 2022/10/26
 */
@Slf4j
public class BlockWriteServer {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8077));
        ssc.register(selector, SelectionKey.OP_ACCEPT, null);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel channel = ssc.accept();
                    channel.configureBlocking(false);
                    SelectionKey scKey = channel.register(selector, 0, null);
                    // 向客户端发送大量数据
                    StringBuilder content = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        content.append("a");
                    }
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode(content.toString());
                    int write = channel.write(buffer);
                    log.debug("write: {}",write);

                    if (buffer.hasRemaining()) {
                        // 添加可写事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        scKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    int write = channel.write(buffer);
                    log.debug("write: {}",write);

                    if (!buffer.hasRemaining()) {
                        // 取消可写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        key.attach(null);
                    }
                }

            }
        }
    }
}
