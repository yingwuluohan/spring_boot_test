package com.unisound.iot.controller.jdk.AQS;

import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @Created by yingwuluohan on 2020/3/17.
 */
public class SynchronizedLock {

    /**
     * 当前加锁状态，记录加锁次数
     *
     */
    private volatile int state = 0;

    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;

    private ConcurrentLinkedQueue< Thread > waiters = new ConcurrentLinkedQueue<>();




    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public boolean aquire(){
        //cas 比较与交换 原子算法
        Thread current = Thread.currentThread();
        //初始状态
        int c = getState();
        if( 0 == c ){//同步器还没有被持有
            //如果当前线程是刚刚被唤醒的队首线程 也可以
            if( ( waiters.size() == 0||current == waiters.peek() ) && compareAndSqapState( 0 ,1 ) ){
                setLockHolder( current );
                return true;
            }
        }
        return false;
    }

    public void lock(){
        if( aquire() ){
            return;
        }

        //Cas 比较交换-原子算法
        Thread current = Thread.currentThread();
        waiters.add( current );
        for( ; ; ){

            if( current == waiters.peek() && aquire() ){
                //线程拿到锁后从队列中移除
                waiters.poll();
                return;
            }
            //TODO 阻塞当前线程，释放CPU的使用权
            LockSupport.park( current );//保存对线程的引用
        }

    }

    public void unlock(){
        if( Thread.currentThread() != lockHolder ){

        }
        int state = getState();
        if( compareAndSqapState( state , 0 )){
            setLockHolder( null );
            //
            Thread first = waiters.peek();
            if( first != null ){
                LockSupport.unpark( first );
            }
        }
    }

    //原子操作
    public final boolean compareAndSqapState( int except ,int update){
        return unsafe.compareAndSwapInt( this , stateoffset ,except, update );
    }

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    public static final long stateoffset;

    static{
        try{
            stateoffset = unsafe.objectFieldOffset( SynchronizedLock.class.getDeclaredField( "state"));
        }catch (Exception e ){
            throw new Error();
        }
    }














}
