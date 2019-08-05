package com.unisound.iot.controller.dubbo.rpcProtocol.dubboProtocol;

import com.unisound.iot.controller.dubbo.frame.Invocation;
import com.unisound.iot.controller.dubbo.frame.Protocol;
import com.unisound.iot.controller.dubbo.frame.Url;
import com.unisound.iot.controller.dubbo.rpcProtocol.dubboProtocol.netty_client.NettyClient;
import com.unisound.iot.controller.dubbo.rpcProtocol.dubboProtocol.netty_server.NettyServer;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 */
public class DubboProtocol implements Protocol {
    @Override
    public void start(Url url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start( url.getHost() ,url.getPort());
    }

    @Override
    public String send(Url url, Invocation invocation) {
        NettyClient nettyClient = new NettyClient();
        String result = nettyClient.send( url.getHost() ,url.getPort() ,invocation );

        return result;
    }
}
