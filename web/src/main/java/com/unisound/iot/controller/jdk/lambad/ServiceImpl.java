package com.unisound.iot.controller.jdk.lambad;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements LambadApi{


    private Map< Integer , String > map = new ConcurrentHashMap<>();


    public static void getServiceInfo( String key , Integer num ){

        System.out.println( "getServiceInfo执行 key ：" + key + ",num:" + num);
    }

    public String getStrInfo(){

        return "s";
    }

    public static String getStr( LambadApi key ,String str ) {
        String result = key.appliy();
        return result + str ;
    }


    public static String getTest( String str ){

        System.out.println( "getTest 执行：" + str );
        return str;
    }

    @Override
    public String appliy() {
        System.out.println( "子类基础执行***" );
        return "str";
    }
}
