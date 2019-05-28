package com.unisound.iot.controller.websocket;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClient extends WebSocketClient {

    static Logger logger = Logger.getLogger(MyWebSocketClient.class);

    private String url ;



    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
// TODO Auto-generated method stub
        logger.info("------ MyWebSocket onOpen ------");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
// TODO Auto-generated method stub
        logger.info("------ MyWebSocket onClose ------");
    }

    @Override
    public void onError(Exception arg0) {
// TODO Auto-generated method stub
        logger.info("------ MyWebSocket onError ------");
    }

    @Override
    public void onMessage(String arg0) {
// TODO Auto-generated method stub
        logger.info("-------- 接收到服务端数据： " + arg0 + "--------");
    }
    private static byte[] audioData = new byte[1024 * 1024 ];
    public static void main(String[] args) {
        try {
            URI uri = new URI( "ws://127.0.0.1:8080/websocket" );
            MyWebSocketClient e = new MyWebSocketClient( uri );
            e.connect();


            while(!e.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                logger.info("正在连接...");
            }
            byte[] data = new byte[ 1024 ];
            e.send( "test hellow " );
            try {
                FileInputStream inputStream = new FileInputStream( new File( "D://feedback_type.sql" ) );
                int length = 0 ;
                long audioLength = 0;
                while( ( length = inputStream.read( data )) > 0 ){

                    System.out.println( "發送二進制數據：" + data );
//                    System.arraycopy(data, 0, audioData, 0, length);
                    audioLength += length;
                    e.send( data );
                    System.out.println( "文件叠加后的长度:" + audioLength );
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}