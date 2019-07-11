package com.unisound.iot.controller.netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MySocketServer {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//接收连接。把连接给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{


            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group( bossGroup ,workerGroup).
                    channel(NioServerSocketChannel.class).
                    handler( new LoggingHandler( LogLevel.INFO)).
                    childHandler( new WebSocketChannelInitialiser() );
            //监听端口
            ChannelFuture channelFuture = serverBootstrap.bind( 8888 ).sync();
            channelFuture.channel().closeFuture().sync();
        }catch ( Exception e ){

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
