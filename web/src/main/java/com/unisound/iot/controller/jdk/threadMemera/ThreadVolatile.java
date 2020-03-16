package com.unisound.iot.controller.jdk.threadMemera;

/**
 * @Created by yingwuluohan on 2020/3/16.
 */
public class ThreadVolatile {

    //TODO 如果没有volatile变量 则线程1while 循环后面的代码永远都不会执行，两个线程
    //TODO 的内存信息都拷贝了副本，没有强制可见
    private static volatile boolean  isTrue = false;


    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println( "执行thread" );
                while( !isTrue ){

                }
                System.out.println( "执行thread 完毕" );
            }
        }) .start();

        Thread.sleep( 2000 );

        new Thread(new Runnable() {
            @Override
            public void run() {
                get();
            }
        }).start();
    }


    public static void get(){
        System.out.println( "-----------" );
        isTrue = true ;
        System.out.println( "执行get方法完毕" );
    }

}
