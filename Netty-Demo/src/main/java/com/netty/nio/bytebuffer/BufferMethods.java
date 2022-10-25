package com.netty.nio.bytebuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * buffer常用方法
 * @author zhaixinwei
 * @date 2022/10/25
 */
public class BufferMethods {

    public static void main(String[] args) throws IOException {
        // 1. 为buffer分配内存空间
        // 为buffer分配，jvm堆内存空间
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 为buffer分配，直接内存空间
        ByteBuffer bufferDirect = ByteBuffer.allocateDirect(10);

        // 2. 向buffer中写入数据
        // 从channel中写入buffer
        FileChannel channel = new FileInputStream("tmp.txt").getChannel();
        channel.read(buffer);
        // 调用put方法写入buffer
        buffer.put((byte) 0x61);

        // 3. 从buffer中获取数据
        // 将buffer数据写出到channel
        channel.write(buffer);
        // get方法获取buffer中数据
        buffer.get();

        // 重置position为0，重新从0开始读取数据
        buffer.rewind();
        // mark()和reset()方法，mark对position的位置做一个标记，reset将position重置到mark位置
        buffer.mark();
        buffer.reset();
        // 通过get(index)方法获取任意所有内容，不会影响position
        buffer.get(3);

        // 4. 字符串转ByteBuffer

        // 原始方式
        ByteBuffer buffer1 = ByteBuffer.allocate(10);
        buffer1.put("hello".getBytes(StandardCharsets.UTF_8));

        // 标准CharSet方式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");

        // warp方法
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));

        // butyBuffer转字符串
        StandardCharsets.UTF_8.decode(buffer1);
    }
}
