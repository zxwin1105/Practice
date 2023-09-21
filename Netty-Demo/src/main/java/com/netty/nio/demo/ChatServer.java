package com.netty.nio.demo;

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
 * 群聊服务器demo
 * 核心功能：
 * - 监测用户上下线，并中转到客户端
 * - 中转客户端发送的消息
 *
 * @author seisei
 * @date 2023/7/13
 */
@Slf4j
public class ChatServer {

    // 在线用户
    private final static List<String> USERS = new ArrayList<>();

    // 在线用户Socket连接
    private final static Map<String, SocketChannel> USER_CHANNELS = new HashMap<>();

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(9001));
            log.info("[服务器启动...]");
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 处理事件
            while (true) {
                // 如果没有事件发送阻塞，事件发生继续执行
                log.info("[事件阻塞...]");
                selector.select(500);
                // 获取selector检测到的所有事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    log.info("[事件{}处理...]", next);

                    // 处理完成的事件需要被移除，因为所有的事件都存储在set中
                    iterator.remove();

                    // 客户端访问事件
                    if (next.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) next.channel();
                        // 客户端连接
                        SocketChannel accept = ssc.accept();
                        String hostAddress = getAddr(accept);
                        boolean isSuc = cacheUser(accept);
                        // 客户端已经连接
                        if (!isSuc) {
                            continue;
                        }
                        // 为客户端添加读事件，监测客户端发送信息
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);

                        String msg = "Server>>" + hostAddress + "已登录";
                        log.info("[用户登录：{}]", hostAddress);
                        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(msg);
                        notice(byteBuffer);
                    } else if (next.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try {
                            SocketChannel socketChannel = (SocketChannel) next.channel();

                            // 接收客户端发送的消息
                            byteBuffer.clear();
                            int read = socketChannel.read(byteBuffer);
                            String addr = getAddr(socketChannel);
                            // 判断是否客户端正常断开
                            if (-1 == read) {
                                log.info("[客户端断开:{}]", addr);
                                USERS.remove(addr);
                                USER_CHANNELS.remove(addr);
                                String msg = "Server>>" + addr + "已下线";
                                byteBuffer = StandardCharsets.UTF_8.encode(msg);
                                notice(byteBuffer);
                                continue;
                            }
                            // 发送给所有在线的用户
                            socketChannel.read(byteBuffer);
                            String msg = addr + ">>";
                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.limit()];
                            for (int i = 0; i < byteBuffer.limit(); i++) {
                                bytes[i] = byteBuffer.get(i);
                            }
                            msg = msg + new String(bytes);
                            log.info("[中转消息：{}]", msg);
                            byteBuffer.clear();
                            byteBuffer.put(msg.getBytes());
                            notice(byteBuffer);
                        } catch (IOException e) {
                            next.cancel();
                        }

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void notice(ByteBuffer byteBuffer) {
        Collection<SocketChannel> values = USER_CHANNELS.values();
        Iterator<SocketChannel> iterator = values.iterator();
        // 切换byteBuffer模式
//        byteBuffer.flip();
        // 发送消息
        try {
            while (iterator.hasNext()) {
                SocketChannel next = iterator.next();
                next.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean cacheUser(SocketChannel accept) {

        String hostAddress = accept.socket().getInetAddress().getHostAddress();
        System.out.println(accept.socket().getInetAddress().getHostAddress());
        if (USERS.contains(hostAddress)) {
            log.info("[cacheUser] 用户已登录");
            return false;
        }
        USERS.add(hostAddress);
        USER_CHANNELS.put(hostAddress, accept);
        return true;
    }


    private static String getAddr(SocketChannel channel) {
        return channel.socket().getInetAddress().getHostAddress();
    }
}
