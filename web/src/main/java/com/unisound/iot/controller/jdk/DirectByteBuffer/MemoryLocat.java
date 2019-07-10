package com.unisound.iot.controller.jdk.DirectByteBuffer;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class MemoryLocat   {


    private static Direct direct;



    public PhantomReference setReference(Direct direct ){
        ReferenceQueue referenceQueue= new ReferenceQueue<>();

        PhantomReference phantomReference= new PhantomReference( direct ,referenceQueue);
        phantomReference.get();
        return phantomReference;
    }


    public static void main(String[] args) {
        MemoryLocat me = new MemoryLocat();
        direct = new Direct();
        direct.setId( 2 );
        direct.setName( "name" );
        PhantomReference reference = me.setReference( direct );
        System.out.println( reference.get() );
        System.out.println( "结果:" + ((Direct)reference.get()).getName() );
    }

}
