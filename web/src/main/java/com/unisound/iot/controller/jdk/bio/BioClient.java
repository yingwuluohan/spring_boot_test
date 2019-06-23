package com.unisound.iot.controller.jdk.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        //建立连接
        socket.connect( new InetSocketAddress( "127.0.0.1" , 9099 ));
        Scanner scanner = new Scanner( System.in );
        System.out.println( "请输入内容：");

        while ( true ){
            String next = scanner.next();
            socket.getOutputStream().write( next.getBytes() );
        }

    }
}
