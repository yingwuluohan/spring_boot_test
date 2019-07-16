package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class Buffer_ {


    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate( 20 );

        System.out.println( "capacity:" + intBuffer.capacity() );
        for( int i = 0 ; i < 5 ;i++ ){
            int random = new SecureRandom().nextInt( 30 );
            intBuffer.put( random );
        }

        System.out.println( "循环后大小limit：" + intBuffer.limit() );

        intBuffer.flip();
        System.out.println( "buffer 调用flip后：" + intBuffer.limit() );
        System.out.println( "enter while loop ");

        while ( intBuffer.hasRemaining() ){
            System.out.println( "position:"+ intBuffer.position() );
            System.out.println( "limit:"+ intBuffer.limit() );
            System.out.println( "capacity:"+ intBuffer.capacity() );

            System.out.println( "get:"+intBuffer.get() );
        }

    }















}
