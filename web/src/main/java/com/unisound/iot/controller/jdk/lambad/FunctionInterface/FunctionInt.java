package com.unisound.iot.controller.jdk.lambad.FunctionInterface;

/**
 * 有且只有一个抽象方法的接口，即为函数接口
 *
 *  FunctionalInterface检测接口是否为函数式接口！
 *
 */
@FunctionalInterface
public interface FunctionInt {

     void method();

    default  String getInfo(){
        return "test";
    }



    
}
