package com.unisound.iot.controller.jdk.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁  : 优点：不会阻塞，缺点：但长时间循环会消耗CPU
 *  Unsafe  ，CAS
 *
 *  通过CAS完成自旋操作
 */
public class SelfCyclicLock {

    //原子引用线程,
    AtomicReference< Thread > atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SelfCyclicLock self = new SelfCyclicLock();
        new Thread( () -> {
            try{
                self.myLock();

                TimeUnit.SECONDS.sleep( 5 );
                self.myUnlock();
            }catch ( Exception e ){

            }
        } , "t111" ).start();
        new Thread( () -> {
            try{
                self.myLock();
                TimeUnit.SECONDS.sleep( 1 );
                self.myUnlock();
            }catch ( Exception e ){

            }

        } ,"t2222").start();
    }

    public void myLock(){
        Thread thread = Thread.currentThread();

        System.out.println( thread.getName() + "加锁");

        do{

        }while ( !atomicReference.compareAndSet( null ,thread ));

    }


    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet( thread , null );
        System.out.println( Thread.currentThread().getName() + "解锁");

    }









}
