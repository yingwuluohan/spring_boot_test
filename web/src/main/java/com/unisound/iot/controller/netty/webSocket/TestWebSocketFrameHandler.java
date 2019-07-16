package com.unisound.iot.controller.netty.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

//处理文本
public class TestWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println( "收到消息：" + msg.getText() );
        ctx.channel().writeAndFlush( new TextWebSocketFrame( "" + LocalDateTime.now() ));
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        System.out.println( "handlerAdded" );

    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "" + ctx.channel().id().asLongText() );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println( "" + ctx.channel().id().asLongText() );
    }









}
