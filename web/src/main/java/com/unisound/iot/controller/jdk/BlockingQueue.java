package com.unisound.iot.controller.jdk;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company 北京云知声技术有限公司
 */
public class BlockingQueue {

    /** 先进先出 */
    private static ArrayBlockingQueue arrayQueue = new ArrayBlockingQueue( 11 );
    private static LinkedBlockingQueue linkQueue = new LinkedBlockingQueue( );

    /**
     * poll : 若队列为空，返回null ,非阻塞
     2. Remove: 若队列为空，抛出NoSuchElementException异常
     3. Take : 若队列为空，发生阻塞，等待有元素
     */
    public static synchronized void test(){

        arrayQueue.add( "a" );
        linkQueue.add( "1" );
        try {
            arrayQueue.put( "b" );
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
