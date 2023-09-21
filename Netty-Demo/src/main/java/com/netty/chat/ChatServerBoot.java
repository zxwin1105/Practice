package com.netty.chat;

import com.netty.chat.message.ChatReqMessage;
import com.netty.chat.message.ChatRespMessage;
import com.netty.chat.message.LoginReqMessage;
import com.netty.chat.message.LoginRespMessage;
import com.netty.chat.protocol.ProtocolCodec;
import com.netty.chat.session.Session;
import com.netty.chat.user.UserInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author seisei
 * @date 2023/9/13
 */
@Slf4j
public class ChatServerBoot {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 共享处理器
        final LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        final ProtocolCodec protocolCodec = new ProtocolCodec();


        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        // 日志handler
                        ch.pipeline().addLast(loggingHandler);
                        // 半包/粘包handler
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(4096, 12, 4, 0, 0));
                        // 协议解析处理器 （双向），输出Message类型
                        ch.pipeline().addLast(protocolCodec);

                        // 空闲连接检测
                        ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
                        // ChannelDuplexHandler双向处理器
                        ch.pipeline().addLast(new ChannelDuplexHandler(){
                            // 自定义事件
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                // 处理IdleStateHandler中的事件
                                if (evt instanceof IdleStateEvent) {
                                    IdleStateEvent e = (IdleStateEvent) evt;
                                    if (e.state() == IdleState.READER_IDLE) {
                                        // 约定时间内没有收到客户端读信息，关闭连接。但是用户可能不会一直输入，需要引入心跳机制
                                        log.info("无应答，关闭连接");
                                        ctx.close();
                                    }
                                }

                            }
                        });

                        // 处理登录消息
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginReqMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, LoginReqMessage msg) throws Exception {
                                boolean login = UserInfo.login(msg);
                                LoginRespMessage loginRespMessage = new LoginRespMessage(login, "登录失败");
                                if (login) {
                                    // 设置session;
                                    boolean isSave = Session.build().save(msg.getUsername(), ctx.channel());
                                    if (isSave) {
                                        loginRespMessage.setMsg("登录成功");
                                    }
                                }
                                // 响应
                                ctx.writeAndFlush(loginRespMessage);
                            }
                        });
                        // 处理消息信息
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatReqMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatReqMessage msg) throws Exception {
                                String to = msg.getTo();
                                // 响应信息
                                ChatRespMessage chatRespMessage = new ChatRespMessage(true, "消息发送成功");

                                Session session = Session.build();
                                Channel channel = session.getChannel(to);

                                if (channel == null) {
                                    chatRespMessage.setFlag(false);
                                    chatRespMessage.setMsg("发送失败，对方不在线");
                                    ctx.writeAndFlush(chatRespMessage);
                                    return;
                                }
                                channel.writeAndFlush(msg);
                                ctx.writeAndFlush(chatRespMessage);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                Channel channel = ctx.channel();
                                boolean inbound = Session.build().inbound(channel);
                                log.info("用户退出，session inbound {}", inbound);

                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                Channel channel = ctx.channel();
                                boolean inbound = Session.build().inbound(channel);

                                log.info("客户端异常退出，移除session {}, cause:{}", inbound, cause.getMessage());
                            }
                        });

                    }
                });
        Channel channel = serverBootstrap.bind(8899).sync().channel();
        channel.closeFuture().sync();


    }
}
