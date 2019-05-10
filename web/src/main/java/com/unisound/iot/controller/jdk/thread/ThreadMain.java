package com.unisound.iot.controller.jdk.thread;


import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.factory.ProbeAction;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = {"/threadService"},  loadOnStartup = 2)
public class ThreadMain  extends HttpServlet {


    public void init() {
        System.out.println("******servlet init OK******");

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("one request: command is");
        Result engResult = null;
        String id = null;

        try {
            ActionInterface actionInterface = new ProbeAction( "1" );
            ThreadRunner.runTask( actionInterface.getActionLock() );
            System.out.println( "lock 释放锁" );
//            Thread.sleep( 3000 );
            synchronized( actionInterface.getActionLock()  ){
                actionInterface.getActionLock().notifyAll();
            }

            System.out.println( "当前线程：" + Thread.currentThread().getName() );
            System.out.println( "**************************");
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }




}
