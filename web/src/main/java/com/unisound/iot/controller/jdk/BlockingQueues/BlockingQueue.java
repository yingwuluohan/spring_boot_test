package com.unisound.iot.controller.jdk.BlockingQueues;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company
 * 入队都是放到队尾
 * 获取都是从队首
 *
 *
 */
public class BlockingQueue {

    /** 先进先出 */
    private static ArrayBlockingQueue arrayQueue = new ArrayBlockingQueue( 11 );
    private static LinkedBlockingQueue linkQueue = new LinkedBlockingQueue( );
    private static SynchronousQueue sylinkQueue = new SynchronousQueue( );

    /**
     * poll :移除元素 ， 若队列为空，返回null ,非阻塞
     2. Remove: 若队列为空，抛出NoSuchElementException异常
     3. Take : 若队列为空，发生阻塞，等待有元素
     */
    /**
     * 添加方法
     * offer
     * add
     * put
     */
    public static synchronized void test(){

        int num = Integer.MAX_VALUE;

        try {
            Object result = arrayQueue.poll(  );
            System.out.println( result );
            arrayQueue.put( "a" );
            arrayQueue.add( "a" );
            arrayQueue.put( "b" );

            linkQueue.add( "1" );
            linkQueue.put( "2" );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrayQueue.offer( "c" );
        arrayQueue.offer( "d" );
        linkQueue.offer( "3" );
        linkQueue.offer( "4" );
    }

    public static void main(String[] args) {
        test();
        System.out.println( "arrayQueue 头数据移除：" + arrayQueue.remove() );
        try {
            System.out.println( "arrayQueue 头数据" + arrayQueue.take( ));
            System.out.println( "linkQueue 头数据" + linkQueue.take( ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "arrayQueue 头数据" + arrayQueue.poll() );
        System.out.println( "arrayQueue size :" + arrayQueue.size() );
        System.out.println( "linkQueue 头数据" + linkQueue.poll() );
        System.out.println( "linkQueue size :" + linkQueue.size() );
    }










}
