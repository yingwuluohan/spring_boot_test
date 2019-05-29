package com.unisound.iot.controller.websocket.javax_websocket;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

@Component
//@WebListener//配置Listener
public class RequestListener implements ServletRequestListener {


    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {
        System.out.println( "RequestListener");
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
        System.out.println( "requestDestroyed" );

    }



}