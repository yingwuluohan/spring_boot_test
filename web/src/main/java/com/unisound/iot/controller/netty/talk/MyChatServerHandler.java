package com.unisound.iot.controller.netty.talk;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Created by yingwuluohan on 2019/7/11.
 * @Company 北京云知声技术有限公司
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /** ChannelGroup 可以存放多个channel 连接 ， 以便服务端接收到客户端连接后对客户端做批量处理 */
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE) ;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        //怎样获得自己的连接，channelGroup也存放了自己的连接，上线时不需要提示自己
        Channel channel = ctx.channel();
        channelGroup.forEach( ch ->{
            if( channel != ch ){
                ch.writeAndFlush( ch.remoteAddress() +"发消息了" );
            }else{
                ch.writeAndFlush( "自己发消息" );
            }
        });


    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx )throws Exception{
        Channel channel = ctx.channel();
        channelGroup.add( channel );
        channelGroup.writeAndFlush( "[ 客户端 ：]" + channel.remoteAddress() + "上线");
    }

    /** 断开连接时回调 */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush( "[ 客户端 ：]" + channel.remoteAddress() + "离线");
        //当有客户端连接断开时 netty 会自动处理channelGroup 中的连接，不需要手动干预
        /** eg */
        System.out.println( "channelGroup 连接剩余：" + channelGroup.size() );

    }

    /** channel处于活动状态 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
