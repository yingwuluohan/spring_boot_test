package com.unisound.iot.controller.jdk.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) {
//        MDC.put();

        //屏障，阻拦3个线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1正在执行");
                try {
                    // 等待
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程1运行结束，时间： " + System.currentTimeMillis());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2正在执行");
                try {
                    // 等待
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程2运行结束，时间： " + System.currentTimeMillis());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程3正在执行");
                try {
                    //线程3阻塞2秒，测试效果
//                    Thread.sleep(2000);
                    // 等待
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程3运行结束，时间： " + System.currentTimeMillis());
            }
        }).start();

    }
}
