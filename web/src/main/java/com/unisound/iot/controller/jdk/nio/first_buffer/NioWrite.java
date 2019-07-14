package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioWrite {


    public static void main(String[] args) throws  Exception {
        FileOutputStream fileOutputStream = new FileOutputStream( "D://niotest.txt" );
        FileChannel fileChannel = fileOutputStream.getChannel() ;
        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );

        byte[] message = "kdsfjhi980wek".getBytes();
        for( int i =0; i < message.length;i++ ){
            //内容以经督导了byteBuffer中
            byteBuffer.put( message[ i ]);
        }

        //再通过buffer 写入到channel中,进行翻转
        byteBuffer.flip();
        fileChannel.write( byteBuffer );
        fileChannel.close();


    }
}
