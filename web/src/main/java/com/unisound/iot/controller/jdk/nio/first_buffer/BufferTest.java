package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class BufferTest {


    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate( 1024 );

        for( int i = 0 ; i< intBuffer.capacity();i++ ){
            int random = new SecureRandom().nextInt( 20 );
            System.out.println( random );
        }
    }

    //javaIO中最核心的是流 ：stream ,io是面向流的编程,
    /***
     * 程序通过流获取字节信息 ,面向流编程
     * 流只能属于一种：要么是输入流，要么是输出流，不可能两者都同时拥有
     *
     */

    /**
     * NIO中
     * 核心概念：
     * selector：选择器
     * channel：通道
     * buffer：缓冲区
     * 即面向块儿 （block）或者缓冲区buffer 编程的
     *
     *
     */













}
