package com.unisound.iot.controller.jdk.thread.join;

/**
 * @Created by yingwuluohan on 2019/6/16.
 * @Company 北京云知声技术有限公司
 *
 * join : 让当前线程加入cpu执行
 */
public class Join extends Thread {


    public static void main(String[] args) throws InterruptedException {

        Join join = new Join();
        Thread thread = new Thread( join );
        thread.start();

        for( int i = 0 ; i < 100 ; i++ ){
            if( i == 50 ){
                thread.join();
            }
            System.out.println( "main:" + i );

        }
    }


    public void run(){
        for ( int i = 0 ; i< 100 ;i++ ){

            System.out.println( "run:" + i );
        }
    }
}
