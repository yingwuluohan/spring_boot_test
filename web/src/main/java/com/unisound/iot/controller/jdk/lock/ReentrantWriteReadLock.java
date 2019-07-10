package com.unisound.iot.controller.jdk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantWriteReadLock {

    private int num =0;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();


    public void write(){
        writeLock.lock();
        try {
            while( num != 0 ){
            writeLock.wait();
            }
            num++;
            System.out.println( Thread.currentThread().getName() + "开始写 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            writeLock.unlock();
        }

    }


    public void read(){
        readLock.lock();
        try {
            while( num == 0 ){
                readLock.wait();
            }
            num--;
            System.out.println( Thread.currentThread().getName() + "开始读---- ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            readLock.unlock();
        }
    }


}

class Manage{


    public static void main(String[] args) {
        ReentrantWriteReadLock reen = new ReentrantWriteReadLock();
        new Thread( () -> {
            for( int i = 0 ;i < 5;i++ ){
                reen.write();
            }

        },"AAAA").start();

        new Thread( () -> {
            for( int i = 0 ;i < 5;i++ ){
                reen.read();
            }

        },"BBBBB").start();
    }

}