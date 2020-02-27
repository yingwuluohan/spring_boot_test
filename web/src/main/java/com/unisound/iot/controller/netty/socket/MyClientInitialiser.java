package com.unisound.iot.controller.netty.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company fn
 */
public class MyClientInitialiser extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE ,0,4,0,4));
        pipeline.addLast( new LengthFieldPrepender( 4 ) );
        pipeline.addLast( new StringDecoder( ));
        pipeline.addLast( new StringEncoder() );


        pipeline.addLast( "MyClientHandler" , new MyClientHandler());
    }
}
