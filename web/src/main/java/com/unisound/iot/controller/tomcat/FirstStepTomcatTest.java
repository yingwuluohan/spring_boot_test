package com.unisound.iot.controller.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Created by yingwuluohan on 2019/7/10.
 * @Company 北京云知声技术有限公司
 */
public class FirstStepTomcatTest {

    private static ExecutorService executor = new ThreadPoolExecutor( 4,
            4 ,2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)  );





    public static void main(String[] args ) throws IOException {
        ServerSocket serverSocket = new ServerSocket( 8088 );
        System.out.println( "server start ****************************" );
        while( !serverSocket.isClosed() ){
            Socket socket = serverSocket.accept();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( "开始接收数据 " );
                    try {
                        InputStream inputStream = socket.getInputStream();
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
                        InputStream serverips = System.in;//在服务端输入广播内容做为输入流
                        OutputStream serverops2 = socket.getOutputStream();//输出流用于传输信息给客户端
                        PrintWriter pw = new PrintWriter(serverops2);
                        //获取信息
                        Scanner sc = new Scanner(serverips);
                        String back="";
                        while(sc.hasNextLine()){
                            back = sc.nextLine();
                            pw.println(back);//输出信息
                            pw.flush();
                            if(back.equals("exit")){
                                System.out.println("服务端讲话完毕，再见！");
                                System.exit(0);
                            }
                        }


                        System.out.println( "end********************************" );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

        }




    }
}
