package com.unisound.iot.controller.jdk.Lock_Cas;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Cas {


    private static AtomicInteger stok = new AtomicInteger();

    public static void main(String[] args ){

        stok.addAndGet( 1 );
        stok.getAndIncrement();
        stok.getAndDecrement();
        System.out.println( 1000<<2 );
        System.out.println( 1000<<3 );

        System.out.println( new Date( 1596695697111L));

    }






















}
