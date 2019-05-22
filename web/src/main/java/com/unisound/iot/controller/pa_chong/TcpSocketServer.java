package com.unisound.iot.controller.pa_chong;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company 北京云知声技术有限公司
 * 1.使用serverSocket创建服务器
 * 2.阻塞式等待accept
 * 3.io出入流
 */
public class TcpSocketServer {

    public static void main(String[] args) {
        Socket socket= null;
        try {
            System.out.println( "服务端启动****" );
            ServerSocket socketSocket = new ServerSocket( 9999 );
            //阻塞等待连接
              socket = socketSocket.accept();
            DataInputStream date = new DataInputStream( socket.getInputStream()) ;
            String data = date.readUTF();
            System.out.println( data );
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
