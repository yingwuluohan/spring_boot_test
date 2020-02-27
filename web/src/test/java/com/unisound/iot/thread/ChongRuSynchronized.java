package com.unisound.iot.thread;

/**
 * @Created by yingwuluohan on 2019/3/17.
 * @Company fn
 */
public class ChongRuSynchronized implements Runnable {
    private int a = 0 ;
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        ChongRuSynchronized instance = new ChongRuSynchronized();
        instance.test1();

    }

    public synchronized   void test1(){
        if( a == 0 ){
            a++;
            System.out.println( " A: " + a );
            test1();

        }

    }
}
