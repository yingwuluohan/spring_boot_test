package com.unisound.iot.controller.jdk.lock;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 独占锁 ：写锁 ，一次只能被一个线程持有 。ReentrantLock 和synchronized 都是独占锁
 *
 * 多个线程同时读，一个线程写  ，即表示整个过程必须是一个完成的过程，中间不允许有其他操作，不能被打断
 *
 * 写操作是：原子+独占 ，
 */

/**
 * 加锁前：
 * 0在写入 **
 2在写入 **
 1在写入 **
 4在写入 **
 3在写入 **
 5在写入 **
 6在写入 **
 7在写入 **
 8在写入 **
 9在写入 **
 0在读取 ----
 1在读取 ----
 2在读取 ----
 3在读取 ----
 4在读取 ----
 1写入完成 **
 3写入完成 **
 5写入完成 **
 6写入完成 **
 7写入完成 **
 9写入完成 **
 4写入完成 **
 2写入完成 **
 0写入完成 *
 */

/**
 * 加读写锁后：
 * 0在写入 **
 0在读取 ----
 1在读取 ----
 2在读取 ----
 3在读取 ----
 4在读取 ----
 0写入完成 **
 1在写入 **
 1写入完成 **
 2在写入 **
 2写入完成 **
 3在写入 **
 3写入完成 **
 4在写入 **
 4写入完成 **
 5在写入 **
 5写入完成 **
 6在写入 **
 6写入完成 **
 7在写入 **
 7写入完成 **
 8在写入 **
 8写入完成 **
 9在写入 **
 9写入完成 **

 */
public class AloneLock {

    public static void main(String[] args) {


        Mycache mycache = new Mycache();
        for( int i = 0 ; i < 10 ;i++ ){

            final int tem = i;
            new Thread( ()-> {
                try {
                    mycache.put( tem+"" , tem );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } , String.valueOf( i )).start();
        }

        for( int i = 0 ; i < 5 ;i++ ){

            final int tem = i;
            new Thread( ()-> {
                try {
                    Object result = mycache.read( tem+"");
                    System.out.println( "读取结果：" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } , String.valueOf( i )).start();
        }
    }



}


class Mycache{

    private volatile Map<String , Object > map = new HashedMap();

//    private Lock lock = new ReentrantLock();//为实现可以有多个线程读，因此不能用该锁

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void put( String key ,Object value ) throws InterruptedException {

        try{
            readWriteLock.writeLock().lock();
            System.out.println( Thread.currentThread().getName() +"在写入 **");
            Thread.sleep( 1000);
            map.put( key ,value );

            System.out.println( Thread.currentThread().getName() +"写入完成 **");
        }catch ( Exception e ){

        }finally{
            readWriteLock.writeLock().unlock();
        }


    }

    public Object read( String key  ) throws InterruptedException {


        try{
            System.out.println( Thread.currentThread().getName() +"在读取 ----");
            TimeUnit.SECONDS.sleep( 1000);
            Object result = map.get( key  );

            System.out.println( Thread.currentThread().getName() +"读取完成 ----");
            return result;
        }catch ( Exception e ){

        }finally {
            readWriteLock.readLock().unlock();
        }

        return null;
    }



}