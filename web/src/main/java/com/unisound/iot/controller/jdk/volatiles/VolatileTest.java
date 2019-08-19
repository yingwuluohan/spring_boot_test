package com.unisound.iot.controller.jdk.volatiles;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Created by yingwuluohan on 2019/8/5.
 */
public class VolatileTest {

    private volatile int num;


    private ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue( 100 );


    public void put(String value ){
        try {

            blockingQueue.put( value );
            num++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public Object take(){
        Object value = null;
        try {
            value = blockingQueue.take();
            num--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

}
