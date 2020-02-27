package com.unisound.iot.thread;

/**
 * @Created by yingwuluohan on 2019/3/17.
 * @Company fn
 */
public class StaticSynchronized implements Runnable {
    static  SynchronizedChuanxing instance1 = new SynchronizedChuanxing();
    static  SynchronizedChuanxing instance2 = new SynchronizedChuanxing();


    @Override
    public void run() {
        test();
    }

    public static void test(){
        System.out.println( "当前线程：" + Thread.currentThread().getName() );
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "结束" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread thread = new Thread( instance1 );
        Thread thread2 = new Thread( instance2 );
        thread.start();
        thread2.start();

        System.out.println( "" + Thread.currentThread().getName()  );
    }
}
