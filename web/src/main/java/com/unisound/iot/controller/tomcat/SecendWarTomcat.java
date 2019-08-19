package com.unisound.iot.controller.tomcat;


import com.unisound.iot.controller.tomcat.projectLoder.ProjectLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 读取实例的war包
 *
 * 根据请求的URL 执行 响应的servlet
 */
public class SecendWarTomcat {

    private static ExecutorService executor = new ThreadPoolExecutor( 4,
            4 ,2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)  );





    public static void main(String[] args ) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        //1. 加载项目
        Map<String ,ProjectLoader.ProjectConfigInfo > projectConfigInfoMap = null;//ProjectLoader.load();



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
                        byte[]  response = "成功接收到请求，这里是Tomcat服务器".getBytes();
                        OutputStream outputStream = request.getOutputStream();
                        //TODO 必须按照http协议返回浏览器才能读的到返回数据
                        outputStream.write( "HTTP/1.1 200 OK \r\n".getBytes() );
                        outputStream.write( "Content-Type: text/html;charset=UTF-8 \r\n".getBytes() );
                        outputStream.write( ("Content-Length: 20480 \r\n").getBytes() );

                        outputStream.write( response );
                        outputStream.flush();
                        // 根据URL 匹配具体的servlet请求 ，即需要解析request http请求的协议
                        /**
                         * data:GET / HTTP/1.1
//                         Host: localhost:8088
//                         User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:67.0) Gecko/20100101 Firefox/67.0
//                         Accept: text/html,application/xhtml+xml,application/xml;q=0.9,;q=0.8
//                         Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//                         Accept-Encoding: gzip, deflate
//                         Connection: keep-alive
//                         Cookie: JSESSIONID=4EC457D49C01A9193C12E3EAE399892A
//                         Upgrade-Insecure-Requests: 1
                         **/
                        String firstLine = sb.toString().split("\r\n" )[ 0 ];
                        System.out.println( "firstLine:" + firstLine );
                        String projectName = firstLine.split( " " )[ 1 ].split( "/" )[ 1 ];
                        String servletPath = firstLine.split( " " )[ 1 ].replace( "" + projectName , "" );
                        String servletName = projectConfigInfoMap.get( projectName ).servletsMapping.get( servletPath );
                        Servlet servlet = projectConfigInfoMap.get(projectName ).servletsInstances.get( servletName );

                        ServletRequest request1 = createRequest();
                        ServletResponse response1 = createResponse();

                        try {
                            servlet.service( request1 , response1 );
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }

                        System.out.println( "结束 " );
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



    private static HttpServletRequest createRequest(){
        return null;
    }

    private static HttpServletResponse createResponse(){
        return  null;
    }

}
