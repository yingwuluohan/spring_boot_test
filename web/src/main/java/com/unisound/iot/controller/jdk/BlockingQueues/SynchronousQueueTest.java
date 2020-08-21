package com.unisound.iot.controller.jdk.BlockingQueues;

import java.util.concurrent.SynchronousQueue;

/**
 * @Created by yingwuluohan on 2020/3/19.
 */
public class SynchronousQueueTest {

    private static SynchronousQueue synchronousQueue = new SynchronousQueue( );


    public static void main(String[] args) {
        for( int i =0;i < 10 ;i++ ){
            synchronousQueue.add( i + "queue");
        }

        System.out.println("长度:"+ synchronousQueue.size() );
    }

}
