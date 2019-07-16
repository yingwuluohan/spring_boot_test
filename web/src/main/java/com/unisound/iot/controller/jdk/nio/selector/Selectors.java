package com.unisound.iot.controller.jdk.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Selectors {


    public static void main(String[] args) throws IOException {
        int[] ports = new int[ 5 ];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;
        //
        Selector selector = Selector.open();
//        Set<SelectionKey> set = selector.selectedKeys();

        //把selector注册到监听的端口号上
        System.out.println( );
        for( int  i = 0 ; i < ports.length;i++ ){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking( false );
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress( ports[ i ] );
            serverSocket.bind( address );

            SelectionKey selectionKey = serverSocketChannel.register( selector ,SelectionKey.OP_ACCEPT );
            System.out.println( "监听端口：" + ports[ i ]);


        }
        while ( true ){
            int numbers = selector.select();
            System.out.println( "number:" + numbers );

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //因为监听多个通道
            System.out.println( "selectionKeys：" + selectionKeys );

            Iterator< SelectionKey > iter = selectionKeys.iterator();

            while( iter.hasNext() ){
                SelectionKey selectionKey = iter.next();
                if( selectionKey.isAcceptable() ){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();

                    SocketChannel socketChannel = serverSocketChannel.accept();


                    socketChannel.configureBlocking( false );
                    socketChannel.register(selector , SelectionKey.OP_READ );
                    iter.remove();
                    System.out.println( "获取客户端连接:"+ socketChannel );
                }//如果有数据读写
                else if( selectionKey.isReadable() ){
                    SocketChannel socketChannel  = (SocketChannel)selectionKey.channel();
                    int byteRead =0 ;
                    while( true ){
                        ByteBuffer byteBuffer = ByteBuffer.allocate( 512 );
                        byteBuffer.clear();
                        int read = socketChannel.read( byteBuffer );
                        if( read <= 0 ){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write ( byteBuffer );
                        byteRead += read;
                    }
                    System.out.println( " 读取：" + byteRead + "，来自于" + socketChannel  );
                    //TODO 当前事件已处理完毕，必须remov（）
                    iter.remove();
                }


            }


        }




    }
























}
