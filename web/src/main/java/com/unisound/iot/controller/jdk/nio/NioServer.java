package com.unisound.iot.controller.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioServer {


    public static void main(String[] args) throws InterruptedException {


        //因为每个人发请求的时候都会起socket 连接， 下面的循环要遍历整个list ，而list中大部分的socket是
        // 无效的socket ， 即循环体中很多是无效的循环
        /**
         * OS 中
         * epoll
         * select
         * ServerSocketChannel 调用native C++ 方法驱动 OS 底层函数
         * openJDK --hotspot --
         *
         * 在Linux如何建立一个连接？
         *
         *
         *
         */


        List<SocketChannel> list = new ArrayList<>();

        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );

        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //调用C++ native 方法
            ssc.bind( new InetSocketAddress( 9099 ));
            //设置成非阻塞
            ssc.configureBlocking( false );
            //ssc.register();
            while ( true ){
                Selector selector = Selector.open();
                // 不会阻塞
                SocketChannel socketChannel = ssc.accept();
                if( socketChannel == null ){
                    Thread.sleep( 1000 );
                    System.out.println( "没有人连接" );
                    for( SocketChannel channel : list ){
                        int length = channel.read( byteBuffer );
                        System.out.println( "" + length );
                        if( 0 != length ){
                            byteBuffer.flip();
                            System.out.println( new String( byteBuffer.array() ));
                        }
                    }
                }else{
                    socketChannel.configureBlocking( false) ;
                    list.add( socketChannel );
                    //得到套接字,循环所有的套接字，通过套接字获取数据
                    for( SocketChannel channel : list ){
                        int k = channel.read( byteBuffer );
                        System.out.println( " 接收：" + k );
                        if( k != 0 ){
                            byteBuffer.flip();
                            System.out.println( new String( byteBuffer.array() ));
                        }

                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
