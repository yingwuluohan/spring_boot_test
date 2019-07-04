package com.self.manage.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyServer {

    public void start( String hostName , int port ){
        final ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//        bootstrap.group( eventLoopGroup ).
<<<<<<< HEAD
////                  channel(NioServerSocketChannel.class ).
////                  childHandler( (ChannelInitializer) (socketChannel) ->{
////                      ChannelPipeline pipeline = socketChannel.
=======
//                  channel(NioServerSocketChannel.class ).
//                  childHandler( (ChannelInitializer) (socketChannel) ->{
//                      ChannelPipeline pipeline = socketChannel.
>>>>>>> 3793638e094840566ff63c2234a97671851bf41e
//                  });


    }

















}
