package com.unisound.iot.controller.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class LockFile {

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile( "D://test.txt" ,"rw" );
        FileChannel fileChannel = accessFile.getChannel();
        //true 表示共享锁 ，false 表示排他锁
        FileLock fileLock = fileChannel.lock( 3 , 6 ,true );


        System.out.println( "" + fileLock.isValid() );
        System.out.println( "" + fileLock.isShared() );

        fileLock.release();//释放锁
        accessFile.close();

    }

















}
