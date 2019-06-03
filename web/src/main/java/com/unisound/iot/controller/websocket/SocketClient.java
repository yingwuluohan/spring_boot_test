package com.unisound.iot.controller.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SocketClient {

    static Logger logger = LogManager.getLogger(SocketClient.class);
    public static WebSocketClient client;
    public static void main(String[] args) {
        try {
            client = new WebSocketClient(new URI("ws://10.20.222.77:8480/websocket/parm"),new Draft_6455()) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println( "client *** link" );
                }

                @Override
                public void onMessage(String msg) {
                    logger.info("收到消息=========="+msg);
                    if(msg.equals("over")){
                        client.close();
                    }

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("链接已关闭");
                }

                @Override
                public void onError(Exception e){
                    e.printStackTrace();
                    logger.info("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        logger.info(client.getDraft());
        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            logger.info("正在连接...");
        }
        //连接成功,发送信息
        client.send("哈喽,连接一下啊");

    }

    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("握手成功");
    }
}
