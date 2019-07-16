package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {


    public static void main(String[] args) throws IOException {
        FileInputStream fileInput = new FileInputStream( "D://Linux .txt" );
        FileChannel fileChannel = fileInput.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );
        fileChannel.read( byteBuffer );

        byteBuffer.flip();
        while ( byteBuffer.remaining() > 0 ){
            byte b = byteBuffer.get() ;
            System.out.println( "Character" +(char) b );
        }

        fileInput.close();



    }


















}
