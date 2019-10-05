package com.unisound.iot.controller.jdk.object_header;

import org.openjdk.jol.info.ClassLayout;

public class HotspotHead {

    static HotspotHead h = new HotspotHead();

    public static void main(String[] args) {
        System.out.println( "t" );

        //TODO 打印对象头信息
        System.out.println(ClassLayout.parseInstance( h ).toPrintable() );

        // h 是什么样的锁
        // sync锁住的是对象 ，就是修改h对象的头信息
        synchronized ( h ){
            System.out.println( "lock ------" );
        }












    }













}
