package com.unisound.iot.controller.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
public class Server {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
//        serverBootstrap.group( bossGroup ,workerGroup).channel( NioEventLoopGroup )

    }



}
