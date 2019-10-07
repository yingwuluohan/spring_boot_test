package com.unisound.iot.controller.jdk.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {


    private static final CountDownLatch latch = new CountDownLatch( 1 );
    List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        for( int i = 0 ;i < 100 ;i++ ){
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println( "1111");
                    for( int j = 0 ;j< 10 ;j++ ){

                        System.out.println("线程生产任务：" + num + "，" + j);
                    }

                }
            }).start();


        }
        try {
            System.out.println( "2222");
            Thread.sleep( 2000L );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println( "3333");

    }























}
