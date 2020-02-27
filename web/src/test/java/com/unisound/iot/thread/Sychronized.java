package com.unisound.iot.thread;

/**
 * @Created by yingwuluohan on 2019/3/17.
 * @Company fn
 */
public class Sychronized implements Runnable{

    private  static  int count =0;
    static  Sychronized instance = new Sychronized();
    static  Sychronized instance2 = new Sychronized();

    public static void main(String[] args) {
       Thread thread = new Thread( instance );
       Thread thread2 = new Thread( instance );
        thread.start();
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "" + Thread.currentThread().getName() + count );
    }

    @Override
    public void run() {
        synchronized(this){
            for( int i = 0 ; i< 30000;i++ ){
                count++;
            }
        }


    }

    class count implements Runnable {




        @Override
        public void run() {

        }
    }

}
