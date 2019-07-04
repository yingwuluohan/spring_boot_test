package com.self.distribut.txtransactional.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();



    public void waitTask(){

        try {
            lock.lock();
            condition.await();
        }catch (Exception e ){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }
    public void signalTask(){
        lock.lock();
        condition.signal();
        lock.unlock();

    }










}
