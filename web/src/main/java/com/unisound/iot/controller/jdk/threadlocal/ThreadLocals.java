package com.unisound.iot.controller.jdk.threadlocal;

/**
 * @Created by yingwuluohan on 2019/9/21.
 */
public class ThreadLocals {

    private static ThreadLocal< Info > thread = new ThreadLocal<>();


    public static void main(String[] args) {

        for( int i = 0 ; i < 10 ;i++ ){
            Info info = new Info( i , i + "name" , "code"+i );
            Thread t = new Thread( ()->{

                thread.set( info );
                try {
                    Thread.sleep( 1000L );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println( Thread.currentThread().getName() +":"+ thread.get().getId() );
                thread.remove();
            });
            t.start();
        }




    }




}
