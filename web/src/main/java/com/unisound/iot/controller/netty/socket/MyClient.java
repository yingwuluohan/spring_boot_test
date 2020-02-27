package com.unisound.iot.controller.netty.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company fn
 */
public class MyClient {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group( eventLoopGroup ).channel(NioSocketChannel.class )
                    .handler( new MyClientInitialiser() );
            ChannelFuture channelFuture = bootstrap.connect( "" , 8888 )
                    .sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            eventLoopGroup.shutdownGracefully();
        }


    }














}
