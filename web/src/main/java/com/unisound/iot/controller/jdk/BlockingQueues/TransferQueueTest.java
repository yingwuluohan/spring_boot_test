package com.unisound.iot.controller.jdk.BlockingQueues;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @Created by yingwuluohan on 2020/3/19.
 */
public class TransferQueueTest {

    private static TransferQueue transferQueue = new LinkedTransferQueue();

    public static void main(String[] args) {
        for( int i = 0 ;i < 10;i++ ){
            transferQueue.add( i );
        }

        System.out.println( "长度:" +transferQueue.size() );
    }




}
