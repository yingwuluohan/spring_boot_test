package com.unisound.iot.controller.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer 的
 *  1 .Scattering （将一个channel的数据读到多个buffer里面，
 * 只有当buffer中被读满后才读下一个 ）散开
 * 比如：buffer 中 ，头信息：10个字节， body信息：可变长 ，其它：10个字节， 这就可以用scattering
 *
 *
 * 2. Gathering ：汇聚成一个
 *
 *
 * eg：一个网络程序例子
 */
public class BufferScatteringGathering {


    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress( 8888 );
        serverSocketChannel.socket().bind( address );

        //构建三个长度的buffer

        int messageLengh = 2+ 3 + 4 ;
        ByteBuffer[] byteBuffer = new ByteBuffer[ 3 ];
        byteBuffer[ 0 ] = ByteBuffer.allocate( 2 );
        byteBuffer[ 1 ] = ByteBuffer.allocate( 3 );
        byteBuffer[ 2 ] = ByteBuffer.allocate( 4 );
        //如果读取的字节数据小于总的字节数 9 的话就不断的读取
        SocketChannel socketChannel = serverSocketChannel.accept();

        while( true ){

            int byteRead = 0;
            while ( byteRead < messageLengh ){
                long r = socketChannel.read( byteBuffer );
                byteRead += r;
                System.out.println( "byteRead:" + byteRead );
                Arrays.asList( byteBuffer ).stream().
                        map( buffer ->
                        "postion:" + buffer.position() +
                        ",limit:" + buffer.limit()  ).forEach( System.out:: println );
            }
            Arrays.asList( byteBuffer ).forEach( buffer -> buffer.flip() );

            long bytesWritten = 0 ;
            while( bytesWritten < messageLengh ){
                long r = socketChannel.write( byteBuffer );
                bytesWritten += r;
            }
            Arrays.asList( byteBuffer ).forEach( buffer -> buffer.clear() );


            System.out.println( "byteRead----:" + byteRead );
            System.out.println( "bytesWritten:" + bytesWritten  );

        }


    }














}
