package com.unisound.iot.controller.jdk.lambad.thread_lambad;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company fn
 */
public class RunableLambad {


    public static void startThread( Runnable runnable ){

        new Thread( runnable ).start();

    }

    public static void main(String[] args) {
        startThread( ()->{

        });
    }





}
