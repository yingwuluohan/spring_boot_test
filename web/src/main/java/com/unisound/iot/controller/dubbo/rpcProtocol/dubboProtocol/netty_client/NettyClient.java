package com.unisound.iot.controller.dubbo.rpcProtocol.dubboProtocol.netty_client;

import com.unisound.iot.controller.dubbo.frame.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Created by yingwuluohan on 2019/8/2.
 */
public class NettyClient<T> {


    public NettyClientHandler client = null;

    private static ExecutorService executorService= Executors.newCachedThreadPool();

    public void start( String houstName ,Integer port){


        client = new NettyClientHandler();
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group( group ).channel(NioSocketChannel.class ).
                option(ChannelOption.TCP_NODELAY , true ).
                handler(new ChannelInitializer<Channel>() {
                 protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast( "encoder" ,new ObjectEncoder()).
                addLast( "handler" , (ChannelHandler) client);

            };
        });

        try{
            bootstrap.connect( houstName , port );
        }catch (Exception e ){

        }

    }


    public String send(String hostName , Integer prot , Invocation invocation){

        return "";
    }
}
