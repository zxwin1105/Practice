package com.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zhaixinwei
 * @date 2022/11/1
 */
@Slf4j
public class ByteBufUse {

    public static void main(String[] args) {
//        testIndex();

        composite();
    }

    /**
     * 测试ByteBuf指针
     */
    private static void testIndex() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(16);
        byteBuf.writeBoolean(true);
        log.debug(byteBuf.toString());
        // int占用四个字节
        byteBuf.writeInt(99);
        log.debug(byteBuf.toString());
        log.debug("capacity:{} read:{};write{}", byteBuf.capacity(), byteBuf.readerIndex(), byteBuf.writerIndex());
        log.debug("read byte:{}", byteBuf.readBoolean());
        log.debug("capacity:{} read:{};write{}", byteBuf.capacity(), byteBuf.readerIndex(), byteBuf.writerIndex());
        log.debug("read byte:{}", byteBuf.readInt());
        log.debug("capacity:{} read:{};write{}", byteBuf.capacity(), byteBuf.readerIndex(), byteBuf.writerIndex());
    }

    /**
     * 演示多个ByteBuf组合成一个ByteBuf
     * composite可以将两个ByteBuf组合为一个ByteBuf，且不会发生内存复制
     */
    private static void composite() {
        // 待组合的两个ByteBuf
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(5);
        buf1.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e'});
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(5);
        buf2.writeBytes(new byte[]{'f', 'g', 'k', 'l', 'm'});
        log("buf1:", buf1);
        log("buf2:", buf2);

        // 原始方法合并，需要进行两次copy,
        log.debug("capacity:{} read:{};write{}", buf1.capacity(), buf1.readerIndex(), buf1.writerIndex());
        ByteBuf rawComposite = ByteBufAllocator.DEFAULT.buffer(10);
        // buf1和buf2的读指针会被移动到写指针位置
        rawComposite.writeBytes(buf1).writeBytes(buf2);
        log("rawComposite:", rawComposite);
        log.debug("capacity:{} read:{};write{}", buf1.capacity(), buf1.readerIndex(), buf1.writerIndex());

        // 零拷贝方式，逻辑上合并两个buf

        // 重置读指针
        buf1.resetReaderIndex();
        buf2.resetReaderIndex();
        CompositeByteBuf logicComposite = ByteBufAllocator.DEFAULT.compositeBuffer();
        logicComposite.addComponents(true, buf1, buf2);
        log("logicComposite:", logicComposite);
        log.info("capacity:{}", logicComposite);
        // 毒蜘蛛不对移动
        log.debug("capacity:{} read:{};write{}", buf1.capacity(), buf1.readerIndex(), buf1.writerIndex());
        // 操作loginComposite不会影响buf1
        logicComposite.readByte();
        logicComposite.writeBytes(new byte[]{'n','b','a'});
        log("buf1:", buf1);
        log("buf2:", buf2);
        log("logicComposite:", logicComposite);
        log.debug("capacity:{} read:{};write{}", logicComposite.capacity(), logicComposite.readerIndex(), logicComposite.writerIndex());
        log.debug("capacity:{} read:{};write{}", buf1.capacity(), buf1.readerIndex(), buf1.writerIndex());
        // 注意release()方法
    }

    /**
     * 演示ByteBuf各种拷贝方法
     */
    private static void ByteBufCopy() {
        // 零拷贝

        // #slice()切片，切片后获取的ByteBuf 仍使用的是被切片ByteBuf的内存，只是有自己的读写指针
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        log("buf 初始化后：", buf);
        ByteBuf slice1 = buf.slice(0, 5);
        slice1.retain();
        ByteBuf slice2 = buf.slice(5, 5);
        slice2.retain();

        log("slice1:", slice1);
        log("slice2:", slice2);
        // 修改slice
        slice1.setByte(2, 'z');
        log("修改slice1:", slice1);
        log("修改slice1后的buf:", buf);
        // 释放分片内存，并不会真正释放，而是计数-2，还有buf的计数存在
        slice1.release();
        slice2.release();

        // #duplicate 完全拷贝一份ByteBuf，但是与原ByteBuf使用同一片内存
        ByteBuf duplicate = buf.duplicate();
        duplicate.retain();
        log("duplicate：", duplicate);
        duplicate.setByte(3, 'n');
        duplicate.setByte(5, 'm');
        duplicate.setByte(6, 'l');
        log("修改 duplicate:", duplicate);
        log("修改 duplicate后的buf:", duplicate);
        duplicate.release();

        buf.release();

        // 深拷贝，拷贝出的ByteBuf与之前对象没有关系
        ByteBuf copy = buf.copy();
    }

    public static void log(String msg, ByteBuf byteBuf) {
        System.out.print(msg);
        int capacity = byteBuf.capacity();
        for (int i = 0; i < capacity; i++) {
            System.out.print((char) byteBuf.getByte(i));
        }
        System.out.println();

    }

}
