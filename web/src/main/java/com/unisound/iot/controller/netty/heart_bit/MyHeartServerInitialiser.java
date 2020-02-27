package com.unisound.iot.controller.netty.heart_bit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company fn
 */
public class MyHeartServerInitialiser extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {


        ChannelPipeline pipeline = ch.pipeline();
        //TODO IdleStateHandler 服务端与客户端如果在一定的时间没没有触发读，写，读和写的时间 则触发该event 事件

        //编写处理器
        pipeline.addLast( new IdleStateHandler( 5,10 ,20 , TimeUnit.SECONDS ) );

        pipeline.addLast( new MyHeartServerHandler() );






    }
}
