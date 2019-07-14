package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.nio.ByteBuffer;

public class ByteBuffer_t {


    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );

        ByteBuffer readByteBuffer = byteBuffer.asReadOnlyBuffer();


        System.out.println( "byteBuffer:" + byteBuffer.getClass() );
        System.out.println( "readByteBuffer:" + readByteBuffer.getClass() );

        for( int i = 0 ; i< 5 ;i++ ){
            byteBuffer.putInt( i );
            byteBuffer.putLong( 23L);
            byteBuffer.putChar( i , (char)i );
        }
       // byteBuffer.flip();
        System.out.println( "读数据之前position：" + byteBuffer.position() );
        System.out.println( "读数据之前limit：" + byteBuffer.limit() );
        System.out.println( "byteBuffer.getInt：" + byteBuffer.getInt() );
        System.out.println( "读数据之后position：" + byteBuffer.position() );
        System.out.println( "读数据之后limit：" + byteBuffer.limit() );

        System.out.println( "byteBuffer.getInt：" + byteBuffer.getInt() );
        System.out.println( "byteBuffer.getInt：" + byteBuffer.getInt() );
        System.out.println( "getLong:" + byteBuffer.getLong() );
        System.out.println( "getChar:" + byteBuffer.getChar() );





    }











}
