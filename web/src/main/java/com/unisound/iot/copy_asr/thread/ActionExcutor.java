package com.unisound.iot.copy_asr.thread;


import com.unisound.iot.copy_asr.entity.Actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActionExcutor {
    private static ExecutorService executor = null;
    private static long waitTime = 1000L;

    public ActionExcutor() {
    }

    public static void init() {
        executor = Executors.newCachedThreadPool();
        waitTime = 2000;
    }

    public static void excutor(Actions actions) {
        System.out.println( "execute 启动 ****" );
        executor.execute(new ActionRunner(actions, waitTime));
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
