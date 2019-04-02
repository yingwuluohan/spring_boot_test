package com.unisound.iot.thread;

/**
 * @Created by yingwuluohan on 2019/3/17.
 * @Company 北京云知声技术有限公司
 */
public class SynchronizedChuanxing implements Runnable {
    static  SynchronizedChuanxing instance1 = new SynchronizedChuanxing();
    static  SynchronizedChuanxing instance2 = new SynchronizedChuanxing();


    @Override
    public void run() {
        synchronized( instance1 ){
            System.out.println( "线程instance1开始：" + Thread.currentThread().getName() );
            try {
                Thread.sleep( 3000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( "结束" );
        }

        synchronized( instance2 ){
            System.out.println( "线程instance2开始：" + Thread.currentThread().getName() );
            try {
                Thread.sleep( 3000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( "线程instance2结束" );
        }

    }

    public static void main(String[] args) {
        Thread thread = new Thread( instance1 );
        Thread thread2 = new Thread( instance2 );
        thread.start();
        thread2.start();
//        try {
//            thread.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println( "" + Thread.currentThread().getName()  );
    }
}
