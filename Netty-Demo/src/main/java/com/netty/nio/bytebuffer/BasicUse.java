package com.netty.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * NIO基本使用，读取当前目录一个文件
 *
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class BasicUse {

    public static void main(String[] args) {
        // 获取输入流channel
        try (FileChannel channel = new FileInputStream(Objects.requireNonNull(BasicUse.class.getClassLoader().getResource("tmp.txt")).getFile()).getChannel()) {
            // 定义buffer，并设置buffer缓冲区大小 10byte。用于存储你channel读取的数据。buffer初始为写入模式
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (true) {
                // 从channel读取数据，放入缓冲区buffer中
                int len = channel.read(buffer);
                log.debug("read len:{}",len);
                if (-1 == len) {
                    break;
                }
                // 设置buffer为读模式
                buffer.flip();
                // 从buffer中读取数据
                while(buffer.hasRemaining()){
                    // 从buffer中读取数据
                    byte content = buffer.get();
                    log.info("NIO read content:{}",(char)content);

                }
                // buffer需要继续存储缓冲数据，将buffer设置为写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("NIO read file error");
        }
    }
}
