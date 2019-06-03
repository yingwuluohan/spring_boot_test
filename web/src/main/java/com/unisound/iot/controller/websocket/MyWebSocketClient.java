package com.unisound.iot.controller.websocket;

import com.unisound.iot.controller.websocket.socket_client.WebSocketClient;
import org.apache.log4j.Logger;
import org.java_websocket.handshake.ServerHandshake;

import java.io.*;
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
            URI uri = new URI( "ws://10.20.222.77:8480/websocket/parm" );
            MyWebSocketClient socket = new MyWebSocketClient( uri );
            socket.connect();


            while(!socket.getReadyState().equals( READYSTATE.OPEN)){
                logger.info("正在连接...");
            }
            byte[] data = new byte[ 1024 ];
            socket.send( "test hellow " );
            try {
                while( true ){
                    BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( new File( "D://2019-05-31gc.log" ) ));
                    int length = 0 ;
                    long audioLength = 0;
                    while( ( length = inputStream.read( data )) > 0 ){

                        System.out.println( "發送二進制數據：" + data );
//                    System.arraycopy(data, 0, audioData, 0, length);
                        audioLength += length;
                        socket.send( data );
                        System.out.println( "文件叠加后的长度:" + audioLength );
                    }
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