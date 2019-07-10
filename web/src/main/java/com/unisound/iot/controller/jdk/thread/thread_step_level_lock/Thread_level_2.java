package com.unisound.iot.controller.jdk.thread.thread_step_level_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Product {
    private int num=0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try{
            while( num != 0 ){
                condition.await();
                System.out.println( Thread.currentThread().getName() + num +" ,在等待");

            }
            num++;
            System.out.println( Thread.currentThread().getName() +"增加温度，"+ num);

            //通知唤醒
            condition.signalAll();
        }catch ( Exception e ){

        }finally {
            lock.unlock();
        }

    }


    public void decrement() throws InterruptedException {
        lock.lock();
        try{
            while( num == 0 ){
                condition.await();
                System.out.println( Thread.currentThread().getName() + num +" ,在等待减少");

            }
            num--;
            System.out.println( Thread.currentThread().getName() +"减少温度"+ num);

            //通知唤醒
            condition.signalAll();
        }catch (Exception e ){

        }finally {
            lock.unlock();
        }

    }




}

public class Thread_level_2 {

    public static void main(String[] args) {
        Product instance = new Product();
        new Thread( () -> {
            for ( int i = 0 ;i < 5;i++ ){
                try{
                    instance.increment();
                }catch ( Exception e ){
                    e.printStackTrace();
                }
            }
        } , "AAA" ).start();
        new Thread( () -> {
            for ( int i = 0 ;i < 5;i++ ){
                try{
                    instance.decrement();
                }catch ( Exception e ){
                    e.printStackTrace();
                }
            }
        } , "BB" ).start();
    }



}
