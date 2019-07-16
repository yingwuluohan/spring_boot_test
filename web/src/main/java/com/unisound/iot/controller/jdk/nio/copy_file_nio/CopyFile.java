package com.unisound.iot.controller.jdk.nio.copy_file_nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CopyFile {


    public static void main(String[] args) throws Exception {
        String inputFile = "D://niotest.txt";
        String outputFile = "D://niotestwrite.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile( inputFile , "r" );
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile( outputFile , "rw" );

        long inutLength  = new File( inputFile ).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map( FileChannel.MapMode.READ_ONLY , 0 ,inutLength );

        Charset charset = Charset.forName( "utf-8" );
        CharsetDecoder decoder = charset.newDecoder() ;
        CharsetEncoder encoder = charset.newEncoder();//把字符串转字节数组



        CharBuffer charBuffer = decoder.decode( inputData );
        ByteBuffer outputData = encoder.encode( charBuffer );

        outputFileChannel.write( outputData );

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();


    }
























}
