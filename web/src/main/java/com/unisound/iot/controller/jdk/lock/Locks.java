package com.unisound.iot.controller.jdk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created by yingwuluohan on 2019/7/9.
 * @Company fn
 */
public class Locks {
//    自旋锁
    private volatile int i;

    public void add(){
        i++;
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock( true );
    }


}
