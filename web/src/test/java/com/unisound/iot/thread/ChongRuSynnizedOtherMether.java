package com.unisound.iot.thread;

/**
 * @Created by yingwuluohan on 2019/3/17.
 * @Company 北京云知声技术有限公司
 */
public class ChongRuSynnizedOtherMether implements Runnable {
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        ChongRuSynnizedOtherMether instance = new ChongRuSynnizedOtherMether();
        instance.test1();
    }
    public synchronized  void test1(){
        System.out.println( "test1开始:" + Thread.currentThread().getName() );
        test2();
    }


    public synchronized  void test2(){
        System.out.println( "test2()开始：" + Thread.currentThread().getName() );
    }
}
