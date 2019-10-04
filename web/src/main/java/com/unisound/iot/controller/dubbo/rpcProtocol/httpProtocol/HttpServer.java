package com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 * 自定义Dubbo 框架 利用内置Tomcat
 * HTTP协议
 *
 *
 */
public class HttpServer {


    public void start(String hostName , Integer port ){
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService( "Tomcat" );

        Connector connector = new Connector();
        connector.setPort( port );

        Engine engine = new StandardEngine();
        engine.setDefaultHost( hostName );

        Host host = new StandardHost();
        host.setName( hostName );

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath( contextPath );
        context.addLifecycleListener( new Tomcat.FixContextListener() );

        host.addChild( context );
        engine.addChild( host );

        service.setContainer( engine );
        service.addConnector( connector );

        tomcat.addServlet( contextPath , "" , new DispatcherServlet());
        context.addServletMappingDecoded( "/*" , "dispatcher");



        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

        //tomcat 是一个servlet容器，需要接受请求
        //把servlet 加到Tomcat容器里面

    }











}
