package com.netty.nio.fileChannel;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * 使用channel的transferTo方法进行文件拷贝
 * jdk中所有的transferTo()方法都是采用零拷贝来实现的
 *
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class TransferTo {

    public static void main(String[] args) {
        try (
                FileChannel fromSource = new FileInputStream(Objects.requireNonNull(TransferTo.class.getClassLoader()
                        .getResource("from.txt")).getPath()).getChannel();

                FileChannel toSource = new FileOutputStream("to.txt").getChannel()
        ) {

            for (long left = fromSource.size(); left > 0; ) {
                // transferTo方法每次最多传输2g数据。返回值为实际传输的数据大小
                left -= fromSource.transferTo(fromSource.size() - left,  left, toSource);
            }
        } catch (IOException e) {
            log.error("copy file failed");
        }
    }
}
