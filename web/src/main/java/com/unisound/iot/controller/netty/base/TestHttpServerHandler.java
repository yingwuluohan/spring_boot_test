package com.unisound.iot.controller.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    //读取客户端发送的请求，并返回给客户端结果
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        ByteBuf content = Unpooled.copiedBuffer( "hellow word" , CharsetUtil.UTF_8);
        FullHttpMessage response = new DefaultFullHttpResponse( HttpVersion.HTTP_1_0 ,
                HttpResponseStatus.OK,content );

        response.headers().set( HttpHeaderNames.CONTENT_TYPE ,"text/plain");
        response.headers().set( HttpHeaderNames.CONTENT_LENGTH , content.readableBytes());


    }


}
