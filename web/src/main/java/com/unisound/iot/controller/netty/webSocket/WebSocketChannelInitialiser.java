package com.unisound.iot.controller.netty.webSocket;

import com.unisound.iot.controller.netty.socket.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitialiser extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {


        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE ,0,4,0,4));
        pipeline.addLast( "" , new HttpServerCodec());
        pipeline.addLast( "" , new ChunkedWriteHandler());//以块的形式写
        // 请求分块，分段。已字节的方式聚合最大长度
        pipeline.addLast( "" , new HttpObjectAggregator( 8192 ));// 聚合
        //websocket
        //TODO frames 是webSockt 传递的格式 ：文本的
//        WebSocketFrame
        pipeline.addLast( new WebSocketServerProtocolHandler( "/ws" ));
        pipeline.addLast( "TestWebSocketFrameHandler" , new TestWebSocketFrameHandler() );




    }
}
