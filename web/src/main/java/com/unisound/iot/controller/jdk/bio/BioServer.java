package com.unisound.iot.controller.jdk.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {


    public static void main(String[] args) throws IOException {
        // OS建立套接字文件 ，  Linux里面提供了read( file ,xxx ) 和write( file ,)
        //TODO 负责监听
        ServerSocket serverSocket = new ServerSocket();
        byte[] btyes = new byte[ 1024 ];
        serverSocket.bind( new InetSocketAddress(  9876 ));

        while ( true ){
            System.out.println( "等连接----" );
            //阻塞 ---程序释放CPU资源 ,负责通信,
            Socket accept = serverSocket.accept();
            System.out.println( "获取数据----" );
            //TODO  ，read 是阻塞方法
            //TODO 如果客户端建立连接后没有发送数据，则服务端会一直在阻塞而不会对下一个连接 accept
            int read = accept.getInputStream().read(btyes );
            System.out.println( "连接成功----" );
            String centent = new String( btyes );
            System.out.println( centent );
        }


    }



}
