package com.unisound.iot.controller.jdk.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BioServer_shengji2 {

    public static void main(String[] args) throws IOException {
        // OS建立套接字文件 ，  Linux里面提供了read( file ,xxx ) 和write( file ,)
        //TODO 负责监听
        ServerSocket serverSocket = new ServerSocket();
        byte[] btyes = new byte[ 1024 ];
        serverSocket.bind( new InetSocketAddress(  9876 ));
        List< Socket > list = new ArrayList<>();
        while ( true ){
            System.out.println( "等连接----" );
            //当前连接的线程--阻塞 ---程序释放CPU资源 ,负责通信, 最好这里也可以设非阻塞
            Socket socket = serverSocket.accept();
            if( null == socket ){//比如没有人连接
                //TODO 连接和发数据是两个操作 ， 有可能连接后一直没有发送数据
                // 找到以前连接的socket ，看有没有发送数据
                for( Socket socAccept : list  ){
                    socAccept.getInputStream().read( btyes ) ;
                }

            }else {
                //socket.noBlock(false );//read就可以获取CPU资源
                list.add( socket );
                int read = socket.getInputStream().read(btyes );
                if( read == 0 ){
                    //没有数据


                }else{//有数据

                }
            }
            System.out.println( "获取数据----" );
            //TODO  ，read 是阻塞方法
            //TODO 如果客户端建立连接后没有发送数据，则服务端会一直在阻塞而不会对下一个连接 accept
            //假设下面可以进行非阻塞连接

        }


    }
}
