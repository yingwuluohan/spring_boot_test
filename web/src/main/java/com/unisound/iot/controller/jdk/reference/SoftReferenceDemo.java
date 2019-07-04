package com.unisound.iot.controller.jdk.reference;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {


    public static void main(String[] args) {
        softRef_memory_enouth();
    }

    public static void softRef_memory_enouth(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>( o1 );

        System.out.println( "有内存的强引用：" +o1 );
        System.out.println( "有内存的ruan引用：" +softReference.get()  );

        o1 = null;
        System.gc();
        System.out.println( " GC 之后： ");
        System.out.println( o1 );
        System.out.println( softReference.get());

    }

    public static void softRef_memory_notEnouth(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>( o1 );

        System.out.println( o1 );
        System.out.println( softReference.get()  );

        o1 = null;
        System.gc();
        try{
            byte[] bytes = new byte[ 1024 * 1024 * 100 ];
        }catch (Exception e ){

        }finally {
            System.out.println( o1 );
            System.out.print( softReference.get());
        }
    }


}
