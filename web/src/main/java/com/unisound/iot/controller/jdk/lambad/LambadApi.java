package com.unisound.iot.controller.jdk.lambad;

public interface LambadApi {


    String appliy();


    default String run(){
        System.out.println( "默认方法执行");

        return "lambad";
    }


}
