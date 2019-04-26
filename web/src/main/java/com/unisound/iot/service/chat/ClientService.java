package com.unisound.iot.service.chat;


import com.unisound.iot.api.chart.ClientServiceApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by fn on 2017/5/19.
 */
@Service
public class ClientService implements ClientServiceApi {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 19999;
    private static SocketChannel socketChannel;

    private static Object lock = new Object();

    private static ClientService service;

    public static ClientService getInstance(){
        synchronized (lock) {
            if(service == null){
                try {
                    service = new ClientService();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return service;
        }
    }

    private ClientService() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
    }

    /**
     * 发送消息
     * @param msg
     */
    public void sendMsg(String msg) {
        System.out.println( msg );
        try {
            while (!socketChannel.finishConnect()) {
            }
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     * @return
     */
    public String receiveMsg() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        StringBuffer sb = new StringBuffer();
        int count = 0;
        String msg = null;
        try {
            while ((count = socketChannel.read(buffer)) > 0) {
                sb.append(new String(buffer.array(), 0, count));
            }
            if (sb.length() > 0) {
                msg = sb.toString();
                if ("close".equals(sb.toString())) {
                    msg = null;
                    socketChannel.close();
                    socketChannel.socket().close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

}
