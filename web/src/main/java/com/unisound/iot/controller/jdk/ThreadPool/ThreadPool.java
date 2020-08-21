package com.unisound.iot.controller.jdk.ThreadPool;

import java.util.concurrent.*;

/**
 * @Created by yingwuluohan on 2019/12/4.
 */
public class ThreadPool {


    private static ExecutorService executors = new ThreadPoolExecutor( 2 ,4,
         1000, TimeUnit.SECONDS , new ArrayBlockingQueue<>(4),
            Executors.defaultThreadFactory() ,new ThreadPoolExecutor.CallerRunsPolicy() );


    public static void main(String[] args) {
        for( int i = 0 ;i < 9 ;i++ ){
            Thread thread = new Thread( new ThreadTest( i+"" ) );
            executors.submit( thread );

        }
//        Thread t1 = new Thread( ){
//            @Override
//            public void run() {
//                System.out.println( "*****************" );
////                super.run();
//            }
//        };
//        t1.run();

    }

    static class ThreadTest implements Runnable{
        private String num;



        public ThreadTest(String num ){
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println( "current Thread:" + Thread.currentThread().getName() );
            try {
                Thread.sleep( 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( "测试" + num );

        }
    }



}
