package com.unisound.iot.controller.jdk.nio.chart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ChartServer {


    private static Map<String , SocketChannel > map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking( false );
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind( new InetSocketAddress( 8881 ) );
        //创建selector对象
        Selector selector = Selector.open();
        //将serverSocketChannel注册到selector上 ，
        //一个selector对象可以关联多个channel对象
        serverSocketChannel.register( selector ,SelectionKey.OP_ACCEPT );//关注连接的事件, 当发生时就会触发








        //注册完成后，就行事件的处理
        while( true ){

            try{
                //当有事件发生时就会返回关注的事件数量
                selector.select();
                Set<SelectionKey > selectionKeys = selector.selectedKeys();
                //通过SelectionKey 获取 channel对象
                //判断selectionKeySet的事件类型,根据不同的事件进行对应的处理
                selectionKeys.forEach( selectionKey -> {
                    final SocketChannel client;
                    try{
                        //客户端发起了连接
                        if( selectionKey.isAcceptable() ){
                            //判断当前的事件发生在哪个channel上
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking( false );
                            //注册SocketChannel , 但关注的是数据的读写
                            client.register( selector , SelectionKey.OP_READ );
                            String key = " [ " + UUID.randomUUID().toString() + " ] ";
                            map.put( key , client );


                        }else if( selectionKey.isReadable() ){
                            client= ( SocketChannel)selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate( 1024 );
                            int count = client.read( readBuffer );
                            if( count > 0 ){
                                readBuffer.flip();//翻转
                                Charset charset= Charset.forName( "utf-8" );
                                String receivedMessage = String.valueOf( charset.decode( readBuffer ).array() );

                                System.out.println( client + ":" + receivedMessage );
                                String sendKey = "";
                                for( Map.Entry<String , SocketChannel > entry : map.entrySet() ){
                                    if( client == entry.getValue() ){
                                        //当前客户端
                                        sendKey = entry.getKey();
                                    }
                                }

                                //向所有客户端
                                for(Map.Entry<String , SocketChannel > entry : map.entrySet() ){
                                    //获取已连接的socketChannel对象 , 向所有客户端发送当前的消息
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate( 300 );
                                    writeBuffer.put( ( sendKey + "" + receivedMessage).getBytes() );
                                    writeBuffer.flip();
                                    value.write( writeBuffer );
                                }

                            }

                        }


                    }catch ( Exception e ){

                    }
                    selectionKeys.clear();
                } );


            }catch ( Exception e ){

            }

        }


    }
















}
