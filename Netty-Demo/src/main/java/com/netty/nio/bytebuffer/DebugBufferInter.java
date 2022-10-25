package com.netty.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static com.netty.util.ByteBufferUtil.debugAll;

/**
 * 通过工具类打印出buffer内部在工作期间的变化
 *
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class DebugBufferInter {

    public static void main(String[] args) {
        // 初始化Buffer，此时capacity为10，写入limit为10，position为0
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向buffer中写入一个 16 进制内容
        buffer.put((byte) 0x61);
        // 此时buffer队列中有一个内容，position为1
        debugAll(buffer);

        // 继续向buffer中写入内容
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        // 此时buffer队列中有四个内容，position为4
        debugAll(buffer);

        // buffer仍为写入模式，从buffer中读取一个内容。会读取到position为4的默认内容
        log.info("buffer 为写入模式，从中读取内容：{}",buffer.get());

        // 切换buffer为读模式，此时position为0，limit为4
        buffer.flip();
        debugAll(buffer);

        // 读取buffer中读取一个内容，此时position为1，limit为4
        log.info("buffer 为读取模式，从中读取内容：{}",  buffer.get());
        debugAll(buffer);

        // 使用compact切换为写入模式并压缩队列剩余内容
        buffer.compact();
        debugAll(buffer);
    }
}
