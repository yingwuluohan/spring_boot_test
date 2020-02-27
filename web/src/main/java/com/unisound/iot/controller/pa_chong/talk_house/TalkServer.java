package com.unisound.iot.controller.pa_chong.talk_house;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Created by yingwuluohan on 2019/5/20.
 * @Company fn
 */
public class TalkServer {

    public static void main(String[] args) {
        Socket socket= null;
        try {
            System.out.println( "聊天室服务端启动****" );
            ServerSocket socketSocket = new ServerSocket( 9999 );
            //阻塞等待连接
            socket = socketSocket.accept();
            DataInputStream date = new DataInputStream( socket.getInputStream()) ;
            String data = date.readUTF();
            System.out.println( data );

            //fanhuixiaoxi


            System.out.println( "建立的了连接" );
            date.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
