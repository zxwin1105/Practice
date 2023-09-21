package com.netty.chat.protocol;

import com.netty.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 自定义协议解析器
 *
 * @author seisei
 * @date 2023/9/13
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtocolCodec extends MessageToMessageCodec<ByteBuf, Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        // 编码，用于输出 4字节
        byte[] logic = {1, 2, 3, 4};
        // 版本 1字节
        byte version = 1;
        // 序列化算法 0=jdk 1=json
        byte serialize = 0;
        // 指令类型
        int indexType = msg.getMesType();
        // 请求序号，固定0
        byte serialId = 0;
        // 填空字节
        byte blank = 0;
        // JDK 序列化算法
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        // 消息正文
        byte[] content = bos.toByteArray();
        int length = content.length;

        // 写出内容
        buffer.writeBytes(logic);
        buffer.writeByte(version);
        buffer.writeByte(serialize);
        buffer.writeInt(indexType);
        buffer.writeByte(serialId);
        buffer.writeByte(blank);
        buffer.writeInt(length);
        buffer.writeBytes(content);
        out.add(buffer);
        log.debug("encode -> bytebuf:{}", out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 解码输入message信息
        // 读取四字节的魔数
        int logic = msg.readInt();
        byte version = msg.readByte();
        byte serialize = msg.readByte();
        int indexType = msg.readInt();
        byte serialId = msg.readByte();
        byte blank = msg.readByte();
        int length = msg.readInt();
        log.debug("decode -> 解析内容logic:{},version:{},serialize:{},indexType:{},serialId:{},length:{}",
                logic, version, serialize,indexType, serialId, length);
        // 读取内容
        byte[] content = new byte[length];
        msg.readBytes(content, 0, length);
        // 反序列化内容
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(content));
        Message message = (Message) ois.readObject();
        out.add(message);
        log.debug("decode -> 消息内容message:{}", message);
    }


}
