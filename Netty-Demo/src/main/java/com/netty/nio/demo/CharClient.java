package com.netty.nio.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author seisei
 * @date 2023/7/13
 */
@Slf4j
public class CharClient {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9001));
            log.info("[连接服务器成功...]");
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            Scanner scanner = new Scanner(System.in);
            while(true){
                selector.select(500);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isWritable()){
                        log.info("write"+next.toString());
                    }else if(next.isReadable()){

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel) next.channel();
                        channel.read(byteBuffer);
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.limit()];
                        for (int i = 0; i < byteBuffer.limit(); i++) {
                            bytes[i] = byteBuffer.get();
                            i++;
                        }
                        log.info(new String(bytes));
                    }
                }
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    if ("exit".equalsIgnoreCase(next)) {
                        socketChannel.close();
                        break;
                    }
                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(next);
                    socketChannel.write(byteBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
