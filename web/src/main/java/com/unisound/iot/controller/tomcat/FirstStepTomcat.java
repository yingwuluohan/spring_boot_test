package com.unisound.iot.controller.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirstStepTomcat {

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
                    System.out.println( "开始接收数据 " );
                    try {
                        InputStream inputStream = request.getInputStream();
                        System.out.println( "接收请求 " );
                        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ));
                        String msg = "";
                        StringBuffer sb = new StringBuffer();
                        while ( (msg = reader.readLine()) != null ){
                            if( msg.length() == 0 ){
                                break;
                            }
                            sb.append( msg ).append("\r\n");

                        }
                        System.out.println( "data:" +sb.toString() );
                        //TODO  给浏览器一个响应
                        byte[]  backInfo = "success accept msg back info".getBytes();
                        OutputStream outputStream = request.getOutputStream();
                        //TODO 必须按照http协议返回浏览器才能读的到返回数据
                        outputStream.write( "HTTP/1.1 200 OK".getBytes() );
                        outputStream.write( "Content-Type: text/html;charset=UTF-8".getBytes() );
                        outputStream.write( ("Content-Length: 2048000").getBytes() );

                        outputStream.write( backInfo );
                        outputStream.flush();
                        PrintWriter pw = new PrintWriter(outputStream);// 打印出来
                        pw.print("welcome to my home!");// 打印的信息
                        pw.flush();// 刷新打印

                        System.out.println( "end********************************" );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            request.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

        }




    }
}
