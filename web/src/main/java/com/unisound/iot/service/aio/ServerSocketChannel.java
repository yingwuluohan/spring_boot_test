package com.unisound.iot.service.aio;

import com.unisound.iot.service.aio.protcol.AioSession;
import com.unisound.iot.service.aio.protcol.IoServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class ServerSocketChannel<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSocketChannel.class);
    private AsynchronousServerSocketChannel serverSocketChannel = null;
    private AsynchronousChannelGroup asynchronousChannelGroup;
    protected IoServerConfig<T> config = new IoServerConfig<>();
    private Function<AsynchronousSocketChannel, AioSession<T>> aioSessionFunction;

    public void startServer(Function<AsynchronousSocketChannel, AioSession<T>> aioSessionFunction){
        try {
            asynchronousChannelGroup = AsynchronousChannelGroup.withFixedThreadPool( 4 , new ThreadFactory() {
                byte index = 0;
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "socket:AIO-" + (++index));
                }
            });
            this.serverSocketChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
            if (config.getSocketOptions() != null) {
                for (Map.Entry<SocketOption<Object>, Object> entry : config.getSocketOptions().entrySet()) {
                    this.serverSocketChannel.setOption(entry.getKey(), entry.getValue());
                }
            }
            //bind host
            if (config.getHost() != null) {
                serverSocketChannel.bind(new InetSocketAddress(config.getHost(), config.getPort()), 1000);
            } else {
                serverSocketChannel.bind(new InetSocketAddress(config.getPort()), 1000);
            }

            serverSocketChannel.accept(serverSocketChannel, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                @Override
                public void completed(final AsynchronousSocketChannel channel, AsynchronousServerSocketChannel serverSocketChannel) {
                    System.out.println( "****************aioQuickServer 返回结果:" + channel );
                    serverSocketChannel.accept(serverSocketChannel, this);
                    createSession(channel);
                }

                @Override
                public void failed(Throwable exc, AsynchronousServerSocketChannel serverSocketChannel) {
                    LOGGER.error("smart-socket server accept fail", exc);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
    /**
     * 为每个新建立的连接创建AIOSession对象
     *
     * @param channel
     */
    private void createSession(AsynchronousSocketChannel channel) {
        //连接成功则构造AIOSession对象
        AioSession<T> session = null;
        try {
            session = aioSessionFunction.apply(channel);
//            session.initSession();
        } catch (Exception e1) {
            LOGGER.debug(e1.getMessage(), e1);
            if (session == null) {
                try {
                    channel.shutdownInput();
                } catch (IOException e) {
                    LOGGER.debug(e.getMessage(), e);
                }
                try {
                    channel.shutdownOutput();
                } catch (IOException e) {
                    LOGGER.debug(e.getMessage(), e);
                }
                try {
                    channel.close();
                } catch (IOException e) {
                    LOGGER.debug("close channel exception", e);
                }
            } else {
                session.close();
            }

        }
    }
}
