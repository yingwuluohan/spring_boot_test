package com.unisound.iot.controller.jdk.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer_shengji {

    public static void main(String[] args) throws IOException {
        // OS建立套接字文件 ，  Linux里面提供了read( file ,xxx ) 和write( file ,)
        //TODO 负责监听
        ServerSocket serverSocket = new ServerSocket();
        byte[] btyes = new byte[ 1024 ];
        serverSocket.bind( new InetSocketAddress(  9876 ));

        while ( true ){
            System.out.println( "服务端等连接----" );
            //阻塞 ---程序释放CPU资源 ,负责通信,
            Socket socket = serverSocket.accept();
            System.out.println( "获取数据----" );
            //TODO  ，read 是阻塞方法
            //TODO 如果客户端建立连接后没有发送数据，则服务端会一直在阻塞而不会对下一个连接 accept

            //这样就可以处理客户端并发
            //如果采用多线程可以完成并发处理，子线程自己阻塞：缺点
            //Tomcat7 采用的BIO ，线程之间的上下文切换 ，来一个请求就要开一个线程，
            //如果开启了1000个线程，其实也就有200 多个有效的线程发生实际交互请求
            Thread thread = new Thread( new ExecutSocket( socket ));
            thread.start();
        }


    }

    static class ExecutSocket implements Runnable{

        private  Socket socket;//Linux中就是一个文件

        public ExecutSocket( Socket socket){
            this.socket = socket;
        }
        byte[] btyes = new byte[ 1024 ];
        @Override
        public void run() {
            try {
                socket.getInputStream().read( btyes );
                String content = new String( btyes );

                System.out.println( "服务端接收内容：" + content );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
