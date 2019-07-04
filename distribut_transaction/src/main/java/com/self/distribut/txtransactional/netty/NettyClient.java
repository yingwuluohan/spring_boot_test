package com.self.distribut.txtransactional.netty;


import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NettyClient implements InitializingBean{

    public NettyClientHandler client;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void start( String hostName , Integer port ){
         client = new NettyClientHandler();

        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group( group ).channel(NioSocketChannel.class ).
                option(ChannelOption.TCP_NODELAY , true ).
                handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel( SocketChannel socketChannel ) throws Exception{
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast( "decoder" , new StringDecoder());
                        pipeline.addLast( "encoder" , new StringEncoder());
                        pipeline.addLast( "handler" , client );

                    }
                });



    }

    public void send(JSONObject jsonObject ){

    }
















}
