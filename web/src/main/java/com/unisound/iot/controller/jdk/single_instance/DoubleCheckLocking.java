package com.unisound.iot.controller.jdk.single_instance;

public class DoubleCheckLocking {


    /** 提供私有的静态属性*/
    //TODO 如果没有volatile 其它线程可能访问了一个没有初始化的null的实例
    private static volatile DoubleCheckLocking instance;
    // 构造私有化
    private DoubleCheckLocking(){

    }

    public static DoubleCheckLocking getInstance(){
        //再次检验
        if( null != instance ){
            return instance;
        }

        synchronized ( DoubleCheckLocking.class ){
            if( null == instance ){
                instance = new DoubleCheckLocking();
            }
        }

        return instance;
    }




















}
