package com.unisound.iot.controller.netty.heart_bit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 *
 *从客户端向服务端请求
 */
public class MyHeartServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        //如果是一个事件对象
        if( evt instanceof IdleStateEvent ){
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            String eventType= null;
            switch ( idleStateEvent.state() ){
                case READER_IDLE:
                    eventType= "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType= "写空闲";
                    break;
                case ALL_IDLE:
                    eventType= "quan空闲";
                    break;
            }
            System.out.println( ctx.channel().remoteAddress() + "超时 " + eventType );
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelActive 收到消息：" + ctx );
        ctx.writeAndFlush( "来自服务端的问候" );
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }













}
