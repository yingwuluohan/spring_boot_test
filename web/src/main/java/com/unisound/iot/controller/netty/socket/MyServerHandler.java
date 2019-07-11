package com.unisound.iot.controller.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println( ctx.channel().remoteAddress() + ",msg:" + msg );


        ctx.channel().writeAndFlush( "from server " + System.currentTimeMillis() );


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
