package com.unisound.iot.controller.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {


    /**
     * 第一步 接收BIO请求
     */
    private static ExecutorService executor = new ThreadPoolExecutor( 4,
            4 ,2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)  );



    public static void main(String[] args ) throws IOException {
        ServerSocket serverSocket = new ServerSocket( 8088 );
        System.out.println( "server start ****************************" );
        while( !serverSocket.isClosed() ){
            Socket request = serverSocket.accept();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( "start accept data and print " );
                    try {
                        InputStream inputStream = request.getInputStream();
                        System.out.println( "accept request " );
                        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ));
                        String msg = "";
                        StringBuffer sb = new StringBuffer();
                        while ( (msg = reader.readLine()) != null ){
                            if( msg.length() == 0 ){
                                break;
                            }
                            sb.append( msg );

                        }
                        System.out.println( "data:" +sb.toString() );
                        System.out.println( "+++++++++++++++++++++++++++++++++++");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

        }




    }

/**
 * 需要给http一个交互
 */















}
