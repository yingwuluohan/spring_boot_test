package com.unisound.iot.controller.jdk.reference;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {


    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference< Object > weakReference = new WeakReference<>( o1 );
        System.out.println( "强引用:" + o1 );
        System.out.println( "弱引用:" + weakReference );
        o1 = null;
        System.gc();
        System.out.println( " GC 之后： ");
        System.out.println( "强引用:" + o1 );
        System.out.println( "弱引用:" + weakReference );

    }



















}
