package com.netty.nio.net.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用多线程对之前的非阻塞模式进一步优化。
 * 非阻塞模式存在的缺点：虽然是非阻塞模式，但是在执行事件时，如果事件操作比较耗时，那么整个程序也会被阻塞在事件执行上。
 * 可以利用多线程技术，将事件监测和事件执行进行异步执行。
 *
 * @author zhaixinwei
 * @date 2022/10/26
 */
@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();

        Selector selector = Selector.open();
        ssc.bind(new InetSocketAddress(8678));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT, null);
        Work[] works = new Work[2];
        for (int i = 0; i < works.length; i++) {
            works[i] = new Work("work-" + i);
        }
        AtomicInteger index = new AtomicInteger(0);
        log.debug("server boot");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    log.debug("绑定读事件{}", channel);
                    // 实现对work简单的轮询负载均衡
                    works[index.getAndIncrement() % works.length].register(socketChannel);
                }
            }

        }

    }

    static class Work implements Runnable {

        private Selector selector;
        private Thread thread;
        private volatile boolean start = false;
        private String name;

        Work(String name) {
            this.name = name;
        }

        public void register(SocketChannel channel) throws IOException {
            if (!start) {
                this.thread = new Thread(this, this.name);
                this.selector = Selector.open();
                this.start = !this.start;
                this.thread.start();
            }
            selector.wakeup();
            channel.register(selector, SelectionKey.OP_READ, null);
        }

        @Override
        public void run() {
            // 线程任务，用于处理读写事件
            log.debug("读写事件running...");
            while (true) {
                try {
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            int read = channel.read(buffer);

                            if (-1 == read) {
                                log.debug("客户端关闭");
                                key.cancel();
                            } else {
                                buffer.flip();
                                log.info("read content:{}", StandardCharsets.UTF_8.decode(buffer));
                            }
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
