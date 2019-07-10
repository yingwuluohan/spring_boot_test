package com.unisound.iot.controller.jdk.thread;

/**
 * @Created by yingwuluohan on 2019/7/9.
 * @Company 北京云知声技术有限公司
 */
public class ThreadClass implements Runnable  {


    public static void main(String[] args) {
        Thread thread = new Thread( new ThreadClass( ));
        thread.start();

    }

    @Override
    public void run() {
        System.out.println( "test---" );
    }
}
