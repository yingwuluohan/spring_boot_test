package com.unisound.iot.controller.jdk.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Created by yingwuluohan on 2019/12/16.
 */
public class AqsTest {

    private static AbstractQueuedSynchronizer synchronizer;




    public void getQueue(){


        synchronizer = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) {
                return super.tryAcquire(arg);
            }
        };

        synchronizer.acquire( 1 );
    }


}
