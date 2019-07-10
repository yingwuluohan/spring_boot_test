package com.unisound.iot.controller.netty.base;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
public class TestServerInitialiser extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {


        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast( "" , new HttpServerCodec());
        pipeline.addLast( "TestHttpServerHandler" , new TestHttpServerHandler());




    }
}
