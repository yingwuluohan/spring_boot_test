package com.unisound.iot.controller.jdk.reference;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class WeakReferenceDemo {

    /**
     * 只有空间不够时才会软引用
       只要有GC就会回收弱引用 ：WeakReference

     */
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference< Object > weakReference = new WeakReference<>( o1 );
        System.out.println( "强引用:" + o1 );
        System.out.println( "弱引用:" + weakReference );
        o1 = null;
        System.gc();
        try {
            TimeUnit.MICROSECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( " GC 之后： ");
        System.out.println( "强引用:" + o1 );
        System.out.println( "弱引用:" + weakReference );

    }



















}
