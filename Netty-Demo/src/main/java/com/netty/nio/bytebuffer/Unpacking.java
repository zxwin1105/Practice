package com.netty.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.netty.util.ByteBufferUtil.debugAll;

/**
 * 模拟网络传输时的黏包现象，并进行拆包解决问题
 *
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class Unpacking {

    public void unpack() {
        // 接收端缓存
        ByteBuffer source = ByteBuffer.allocate(32);

        // 模拟接收数据黏包，拆包现象
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(source);
        source.put("w are you\n".getBytes(StandardCharsets.UTF_8));
        split(source);
    }

    private void split(ByteBuffer source) {
        // 切换为读模式
        source.flip();
        // 遍历每一个字节，进行拆包
        for (int i = 0; i < source.limit(); i++) {
            if ('\n' == source.get(i)) {
                // 一个完整的数据
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从source读取数据写入target
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                target.flip();
                log.debug("message: {}",StandardCharsets.UTF_8.decode(target).toString());
            }
        }
        // 本次没有读取完source中的内容，进行数据压缩
        source.compact();
    }

    public static void main(String[] args) {
        new Unpacking().unpack();
    }
}
