package com.unisound.iot.controller.jdk.io;

import java.nio.ByteBuffer;

/**
 * @Created by yingwuluohan on 2019/5/8.
 * @Company fn
 */
public class BytrBufferTest {

    public static void main(String[] args) {
        //非直接缓冲区 ，jvm的内存
        ByteBuffer bb =  ByteBuffer.allocate( 100 );
        //直接缓冲区
        ByteBuffer aa =  ByteBuffer.allocateDirect( 100 );

//        System.out.println( bb.limit() );
//        System.out.println( bb.position());
//
//        bb.put( "abcd".getBytes() );
//        System.out.println( bb.limit() );
//        System.out.println( bb.position());
//
//        System.out.println( "没有flip：" + bb.get( 1 ) );
//        System.out.println( "没有flip：" +bb.get( 2 ) );
//        System.out.println( "limit" +bb.limit() );
//        System.out.println( "position" +bb.position());
//
//        bb.getChar();
//        bb.flip();
//        System.out.println( "flip之后：" + bb.get( 1 ) );
//        System.out.println( "flip之后：" + bb.get( 2 ) );
//        //切换成读取数据模式 flip()
//        System.out.println( "limit" +bb.limit() );
//        System.out.println( "position" +bb.position());

        String word = "kiame3cld";
        String wordc = "ckiame3ld";
        System.out.println( word.startsWith( "c"));
        System.out.println( wordc.startsWith( "c"));

    }
}
