package com.unisound.iot.controller.dubbo.rpcProtocol.dubboProtocol.netty_server;

import com.unisound.iot.controller.dubbo.ServiceProvider.LocalRegister;
import com.unisound.iot.controller.dubbo.frame.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 *
 * Dubbo 协议
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter{


    public void channelRead(ChannelHandlerContext ctx , Object msg) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Invocation invocation = (Invocation)msg;
        Class serviceImp = LocalRegister.get( invocation.getInterfaceName() );
        Method method = serviceImp.getMethod( invocation.getMethodName() ,invocation.getParamsTypes());

        Object result = method.invoke( serviceImp.newInstance(),invocation.getParams());

        System.out.println( "Dubbo 协议" + result.toString() );

        ctx.writeAndFlush( result );
    }








}
