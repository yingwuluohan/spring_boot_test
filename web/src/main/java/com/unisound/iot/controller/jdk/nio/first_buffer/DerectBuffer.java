package com.unisound.iot.controller.jdk.nio.first_buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DerectBuffer {


    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream( "");
        FileOutputStream fileOutputStream = new FileOutputStream( "" );

        FileChannel fileChannel = fileInputStream.getChannel();
        FileChannel outPutChannel = fileOutputStream.getChannel();


        ByteBuffer buffer = ByteBuffer.allocateDirect( 1024 );
        while ( true ){
            buffer.clear();

//            int read = fileInputStream.read( buffer );
        }



    }












}
