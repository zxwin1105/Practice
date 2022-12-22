package com.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxwin
 * @date 2022/10/30
 */
@Slf4j
public class BasicUse {

    public static void main(String[] args) {
        // 初始化ByteBuf，默认容量256kb，可以动态扩容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.directBuffer();

        log.debug("direct: {},{}; heap:{}", buffer, buf1, buf);
        log.debug("init buffer:{}", buffer);

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < 300; i++) {
            str.append("1");
        }
        // str写入到buffer
        buffer.writeBytes(str.toString().getBytes());
        log.debug("write buffer:{}", buffer);

    }

}
