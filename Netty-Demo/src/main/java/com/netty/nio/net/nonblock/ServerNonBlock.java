package com.netty.nio.net.nonblock;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 非阻塞的网络服务端
 *
 * @author zhaixinwei
 * @date 2022/10/26
 */
@Slf4j
public class ServerNonBlock {

    public static void main(String[] args) throws IOException {
        ServerNonBlock serverNonBlock = new ServerNonBlock();
//        serverNonBlock.nonBlock();
        serverNonBlock.unpackMsg();
    }


    public void unpackMsg() throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8067));
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("server boot");
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    // 向selector注册时，可以携带附件，这里将buffer作为事件的附件传递。
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 获取事件附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (-1 == read) {
                            key.cancel();
                        } else {
                            // 处理消息完整性
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                                log.debug("buffer capacity:{}",newBuffer.capacity());
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException exception) {
                        key.cancel();
                    }
                }
            }

        }

    }

    private void split(ByteBuffer buffer) {
        buffer.flip();
        for (int left = 0; left < buffer.limit(); left++) {
            if ('\n' == buffer.get(left)) {
                // 出现分隔符
                int length = left + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int i = 0; i < length; i++) {
                    target.put(buffer.get());
                }
                target.flip();
                log.info("msg:{}", StandardCharsets.UTF_8.decode(target));
            }
        }
        buffer.compact();
    }

    /**
     * 使用Selector的方式实现非阻塞，在有请求连接的时候才工作，没有请求连接是，线程阻塞
     *
     * @throws IOException
     */
    public void selector() throws IOException {
        // 1、创建selector 用于管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8087));
        // 设置ssc非阻塞模式
        ssc.configureBlocking(false);
        // 2、将ssc注册到selector，事件发生后可以通过selectionKey来获取事件类型，处理事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 3、selectionKey设置事件类型
        /*
        事件类型：
        - accept: 服务端在有连接请求时触发
        - connect: 客户端建立连接后触发
        - read: 可读事件
        - write: 可写事件
         */
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("server boot");
        while (true) {
            // 如果没有事件发生时阻塞，发生事件后继续执行
            selector.select();
            // 处理事件，获取所有可以处理的事件集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 需要将处理过的事件移除集合
                iterator.remove();
                if (key.isAcceptable()) {
                    // accept事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    // 处理了事件，会从selectedKeys集合移除。如果没有处理事件会重新将事件加入selectedKeys集合。
                    // 或者调用key.cancel()取消事件
                    SocketChannel socketChannel = channel.accept();
                    log.debug("connected:{}", socketChannel);
                    // 为客户端绑定read事件
                    socketChannel.configureBlocking(false);
                    SelectionKey scKey = socketChannel.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(5);
                        // 客户端正常断开也会触发一个read事件，需要做处理，正常断开返回-1
                        int read = channel.read(buffer);
                        if (-1 == read) {
                            key.cancel();
                        }
                        buffer.flip();
                        log.info("client:{}-msg:{}", channel, StandardCharsets.UTF_8.decode(buffer));
                        buffer.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }


    /**
     * 整个方法需要 客户端debug调用，不能让客户端停止
     * 这种实现非阻塞方法，及时没有请求连接，整个方法也在不断的执行，占用cpu
     *
     * @throws IOException
     */
    public void nonBlock() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8089));
        // 设置ssc非阻塞，调用accept不会被阻塞，如果没有连接返回null
        ssc.configureBlocking(false);

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            SocketChannel socketChannel = ssc.accept();
            if (Objects.nonNull(socketChannel)) {
                // 客户端连接上服务端，read方法阻塞，设置为非阻塞模式
                log.debug("connected:{}", socketChannel);
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }
            // 处理客户端发送的消息
            for (SocketChannel channel : channels) {
                // 将channel中数据写入buffer
                int len = channel.read(buffer);
                // channel 中存在数据
                if (len > 0) {
                    // 从buffer中获取内容
                    buffer.flip();
                    log.info("client:{}-msg:{}", channel, StandardCharsets.UTF_8.decode(buffer).toString());
                    buffer.clear();
                }
            }
        }
    }
}
