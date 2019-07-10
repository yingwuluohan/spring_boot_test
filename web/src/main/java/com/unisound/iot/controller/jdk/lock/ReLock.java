package com.unisound.iot.controller.jdk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（ 又叫递归锁 ）：同一线程外层函数获得锁后，内层递归函数仍然能获取该锁的代码。
 * 同一个线程在外层获取锁的时候，
 * 在进入内层方法会自动获取锁。即线程可以进入任何一个它已经拥有的锁或者同步着的代码块。
   eg：进入自家的大门后，厨房门，卧室的门都可以自由进入了
 */
public class ReLock {

    public synchronized void method1() {
        method2();
    }


    public synchronized void method2() {

    }

    public static void main(String[] args) {
        Test tst = new Test();
        new Thread( () -> {
            try{
                tst.sendE();
            }catch ( Exception e ){

            }
        } , "t111" ).start();
        new Thread( () -> {
            try{
                tst.sendE();
            }catch ( Exception e ){

            }

        } ,"t2222").start();


        Thread t3 = new Thread( new Test2() );
        Thread t4 = new Thread( new Test2() );
        t3.start();
        t4.start();


    }

}
    //synchronized 就是可重入锁 ，即进到方法1了就可以进入方法2，自动获取方法2的锁
    //即防止死锁

class Test{

        public synchronized void sendE() throws Exception{
            System.out.println( "" + Thread.currentThread().getName() + " MSM");
            sendMessage();
        }


        public synchronized void sendMessage() throws Exception{
            System.out.println( "" + Thread.currentThread().getName() + " duanxin");
        }
}

class Test2 implements Runnable{

    Lock lock = new ReentrantLock();


    @Override
    public void run() {
        get();
    }
    public void get(){
        //LOCK必须成对使用

        System.out.println( "get begin " );
        lock.lock();
        try{
            System.out.println( Thread.currentThread().getName() + "get" );
            set();
        }catch ( Exception e ){

        }finally {
            lock.lock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println( Thread.currentThread().getName() + "set" );
        }catch ( Exception e ){

        }finally {
            lock.lock();
        }

    }


}

























