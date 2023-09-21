package com.netty.chat;

import com.netty.chat.message.ChatReqMessage;
import com.netty.chat.message.LoginReqMessage;
import com.netty.chat.message.PingMessage;
import com.netty.chat.protocol.ProtocolCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 聊天客户端
 *
 * @author seisei
 * @date 2023/9/16
 */
@Slf4j
public class ChatClientBoot {

    public static void main(String[] args) throws InterruptedException {
        final LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        final ProtocolCodec protocolCodec = new ProtocolCodec();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 添加处理器
                        ch.pipeline().addLast(loggingHandler);
                        // 解决半包/粘包问题
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(4096, 12, 4, 0, 0));
                        // 编解码处理器
                        ch.pipeline().addLast(protocolCodec);

                        // 客户端心跳机制，
                        ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                        ch.pipeline().addLast(new ChannelDuplexHandler(){
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if (evt instanceof IdleStateEvent) {
                                    IdleStateEvent e = (IdleStateEvent) evt;
                                    // 约定时间内容没有向服务器发送信息，则发送一次心跳
                                    if (e.state() == IdleState.WRITER_IDLE) {
                                        log.info("心跳发送");
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                }
                            }
                        });
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("received msg:{}", msg);
                            }
                            // 连接完成后的事件
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                log.debug("成功连接服务器");
                                new Thread(()->{
                                    // 成功连接服务器后需要用户输入用户名，密码登录操作
                                    Scanner scanner = new Scanner(System.in);
                                    System.out.println("请输入用户名：");
                                    String username = scanner.nextLine();
                                    System.out.println("请输入密码：");
                                    String pwd = scanner.nextLine();
                                    LoginReqMessage loginReqMessage = new LoginReqMessage(username, pwd);
                                    // 发送登录消息
                                    ctx.writeAndFlush(loginReqMessage);
                                    
                                    while(true){
                                        System.out.println("请输入命令：");
                                        System.out.println("=========================");
                                        System.out.println("1.发送命令 send username content");
                                        System.out.println("2.断开连接命令 quit");
                                        System.out.println("=========================");
                                        String line = scanner.nextLine();
                                        String[] command = line.split(" ");
                                        switch (command[0]) {
                                            case "send":
                                                ChatReqMessage chatReqMessage = new ChatReqMessage(username, command[1], command[2]);
                                                ctx.writeAndFlush(chatReqMessage);
                                                break;
                                            case "quit":
                                                ctx.channel().close();
                                                return;
                                        }

                                    }
                                },"input-thread").start();

                            }
                        });
                    }
                });
        Channel channel = bootstrap.connect(new InetSocketAddress(8899)).sync().channel();
        channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
            log.info("断开连接。。。");
            eventExecutors.shutdownGracefully();
        });
    }
}
