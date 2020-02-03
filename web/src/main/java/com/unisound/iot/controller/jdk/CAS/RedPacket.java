package com.unisound.iot.controller.jdk.CAS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Created by yingwuluohan on 2019/11/25.
 */
public class RedPacket {
    private static long balance;

    private static AtomicInteger num= new AtomicInteger( 10);
    private static final int RUNNER_COUNT = 10;

    public RedPacket(long balance, int num) {
        this.balance = balance;
        this.num = new AtomicInteger(num);
    }

    public static long get() {
        long result1 = ThreadLocalRandom.current().nextLong(1, 10 * 2);
        System.out.println( result1 );
        int number = num.get();
        long balan = balance;
        if (balan < 1 || number < 1) {
//            return -1;
        }
        if (num.compareAndSet(number, number - 1)) {
            if (number - 1 == 0) {
                balance = 0;
                return balan;
            }
            long average = balan / number;
            long result = ThreadLocalRandom.current().nextLong(1, average * 2);
            balance -= result;
        }
        return -1;
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread();
        final ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int i = 0; i < RUNNER_COUNT; i++) {
            final int NO = i + 1;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        long num = get();
                        System.out.println("get:" + num + ",,No." + NO + " arrived");
                        Thread.sleep((long)( 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            };
            exec.submit(run);
        }
    }

}
