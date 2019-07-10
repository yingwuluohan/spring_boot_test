package com.unisound.iot.controller.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
//Inbound
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    //读取客户端发送的请求，并返回给客户端结果
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println( "HttpObject msg:" +msg.getClass() );
        System.out.println(" dizhi:"+ ctx.channel().remoteAddress() );

        Thread.sleep( 10000);

        if( msg instanceof HttpRequest ){
            System.out.println( "执行channelHandler");
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println( "方法名称：" +httpRequest.method().name());
            URI uri = new URI( httpRequest.uri() );
            if( "/favicon.ico".equals( uri.getPath() )){
                System.out.println( "请求favicon.ico" );
                return;
            }


            ByteBuf content = Unpooled.copiedBuffer( "hellow word" , CharsetUtil.UTF_8);
            FullHttpMessage response = new DefaultFullHttpResponse( HttpVersion.HTTP_1_0 ,
                    HttpResponseStatus.OK,content );

            response.headers().set( HttpHeaderNames.CONTENT_TYPE ,"text/plain");
            response.headers().set( HttpHeaderNames.CONTENT_LENGTH , content.readableBytes());
            ctx.writeAndFlush( response );
            //channel 本身可以看连接
            ctx.channel().close();
        }



    }
    /** 重写父类的所有方法**/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelActive" );
        super.channelActive( ctx );
    }

    //注册
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        System.out.println( "channelRegistered" );
        super.channelRegistered( ctx );
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        System.out.println( "handlerAdded" );
        super.handlerAdded( ctx );
    }



    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelInactive------" );
        super.channelInactive( ctx );
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelUnregistered------" );
        super.channelUnregistered( ctx );
    }



//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.fireChannelRead(msg);
//        System.out.println( "channelRead" );
//    }
//
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println( "channelReadComplete" );
//        ctx.fireChannelReadComplete();
//    }
//
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        System.out.println( "userEventTriggered" );
//        ctx.fireUserEventTriggered(evt);
//    }
//
//    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//        System.out.println( "channelWritabilityChanged" );
//        ctx.fireChannelWritabilityChanged();
//    }
//
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println( "exceptionCaught" );
//        ctx.fireExceptionCaught(cause);
//    }
}
