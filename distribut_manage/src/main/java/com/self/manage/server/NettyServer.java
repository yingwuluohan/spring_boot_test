package com.self.manage.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public void start( String hostName , int port ){
        final ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group( eventLoopGroup ).
                  channel(NioServerSocketChannel.class ).
                  childHandler( (ChannelInitializer) (socketChannel) ->{
                      ChannelPipeline pipeline = socketChannel.
                  });


    }

















}
