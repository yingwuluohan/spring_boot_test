package com.unisound.iot.controller.netty.heart_bit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Created by yingwuluohan on 2019/7/11.
 * @Company 北京云知声技术有限公司
 */
public class MyHeartServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//接收连接。把连接给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{


            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group( bossGroup ,workerGroup).
                    channel(NioServerSocketChannel.class).
                    //针对bossGroup
                    handler( new LoggingHandler( LogLevel.INFO)).
                    //针对workerGroup
                    childHandler( new MyHeartServerHandler() );
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
