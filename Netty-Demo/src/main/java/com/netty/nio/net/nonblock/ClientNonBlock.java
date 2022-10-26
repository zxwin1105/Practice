package com.netty.nio.net.nonblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaixinwei
 * @date 2022/10/26
 */
public class ClientNonBlock {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(8067));
        channel.write(StandardCharsets.UTF_8.encode("o123456789asdfghjdkl\nasdf\n"));
        channel.write(StandardCharsets.UTF_8.encode("o123\n"));
        System.out.println("wait");
        channel.close();
    }
}
