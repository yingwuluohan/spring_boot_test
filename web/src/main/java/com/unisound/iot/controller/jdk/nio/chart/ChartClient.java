package com.unisound.iot.controller.jdk.nio.chart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartClient {


    public static void main(String[] args) {
        try{
            //输入的操作是个阻塞的操作，所以不能写到主线程里，需要另起一个线程
            //客户端建立连接通过SocketChannel
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking( false );
            Selector selector = Selector.open();
            socketChannel.register( selector , SelectionKey.OP_CONNECT );


            socketChannel.connect( new InetSocketAddress( "" , 8881 ));

            while ( true ){
                //TODO
                selector.select();
                Set<SelectionKey > keySet = selector.selectedKeys();
                for( SelectionKey selectionKey : keySet ){
                    if( selectionKey .isConnectable() ){
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        //是否处于挂起的状态，向完成soecket

                        if( client.isConnectionPending() ){
                            client.finishConnect();
                            ByteBuffer byteBuffer = ByteBuffer.allocate( 200 );
                            byteBuffer.put((LocalDateTime.now() + "连接成功" ).getBytes());
                            byteBuffer.flip();
                            client.write( byteBuffer );

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit( () -> {
                                while ( true ){
                                    try {
                                        byteBuffer.clear();

                                        InputStreamReader input = new InputStreamReader( System.in );
                                        BufferedReader br = new BufferedReader( input );
                                        String message = br.readLine();
                                        byteBuffer.put( message.getBytes() );
                                        byteBuffer.flip();
                                        client.write( byteBuffer );

                                    }catch ( Exception e ){

                                    }
                                }
                            } );

                        }
                        client.register( selector , SelectionKey.OP_READ );
                    }else if( selectionKey.isReadable() ){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                    }
                }

            }


        }catch (Exception e ){

        }
    }


















}
