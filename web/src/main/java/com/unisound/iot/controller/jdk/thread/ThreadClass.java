package com.unisound.iot.controller.jdk.thread;

import java.util.concurrent.*;

/**
 * @Created by yingwuluohan on 2019/7/9.
 */
public class ThreadClass implements Runnable  {

    Executor executors = new ThreadPoolExecutor( 3, 3,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>() ,
            Executors.defaultThreadFactory() ,
            new ThreadPoolExecutor.AbortPolicy() );//拒绝策略



    public static void main(String[] args) {
        Thread thread = new Thread( new ThreadClass( ));
        thread.start();

    }

    @Override
    public void run() {
        System.out.println( "test---" );
        executors.execute(new Runnable() {
            @Override
            public void run() {
                Byte[] bytes = new Byte[ 1024 * 1024 * 50 ];

                System.out.println( "我是线程池的任务--------------" );
            }
        });
    }
}
