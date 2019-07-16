package com.unisound.iot.controller.jdk.lambad.thread_lambad;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company 北京云知声技术有限公司
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
