package com.unisound.iot.controller.jdk.LockSupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created by yingwuluohan on 2020/3/19.
 * 1.线程通信
用两个线程，一个输出字母，一个输出数字，交替输出 1 A 2 B 3 C 4 D 5 E
解析：LockSupport
 */
public class LockSupportTest {

    private Thread t1 ,t2;

//    private LockSupportTest lock = new LockSupportTest();

    String[] word = {"A","B","C","D","E","F","G","H"};
    int[] num = { 1,2,3,4,5,6,7,8 };

    //TODO LockSupport 方案
    public void print(){

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for( int i = 0 ;i < word.length;i++ ){
                    System.out.println( "" + word[ i ]);
                    //唤醒t2
                    LockSupport.unpark( t2 );
                    //z阻塞t1
                    LockSupport.park( t1 );

                }

            }
        });

        t2 = new Thread( () ->{
            for( int i = 0 ;i < num.length;i++ ){
                //唤醒t2
                LockSupport.park( t2 );
                System.out.println( "" + num[ i ]);
                //
                LockSupport.unpark( t1 );
                //
            }
        });

        t1.start();
        t2.start();

    }

    public static void main(String[] args) {
        LockSupportTest l = new LockSupportTest();
//        l.print();

        //wait 方案
        final Object lock = new Object();
        l.printWait( lock );
    }

    //TODO wait 方案
    public void printWait(Object lock){

        new Thread( ()->{
            synchronized ( lock ){
                for( int i = 0 ;i < word.length;i++ ) {
                    System.out.println("" + word[i]);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();//TODO 必须加上notify ，否则程序不能停止
            }
        }).start();

        new Thread( ()->{
            synchronized ( lock ){
                for( int i = 0 ;i < num.length;i++ ){
                    System.out.println( "" + num[ i ]);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                lock.notify();//TODO 必须加上notify ，否则程序不能停止
            }
        } ).start();

    }


    //TODO 第三种：ReentrantLock方式
    public void reetrantLockPrint(){
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread( ()->{
            lock.lock();
            try {
                for( int i = 0 ;i < word.length;i++ ) {
                    System.out.println(word[i] );
                    //唤醒其他线程
                    condition.signal();
                    //阻塞自己
                    condition.await();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                lock.unlock();
            }


        }).start();

    }
























}
