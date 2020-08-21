package com.unisound.iot.controller.jdk.AQS;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Created by yingwuluohan on 2020/3/17.
 */
public class UnsafeInstance {

    public static Unsafe reflectGetUnsafe(){

        try{
            Field field = Unsafe.class.getDeclaredField( "theUnsafe" );
            field.setAccessible( true );
            return ( Unsafe)  field.get( null );
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }


}
