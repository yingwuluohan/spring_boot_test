package com.unisound.iot.controller.jdk.OutOfMemery;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示非堆内存溢出
 * @Created by yingwuluohan on 2019/5/22.
 * @Company 北京云知声技术有限公司
 */
public class Metaspace extends ClassLoader{
    private static int num = 0 ;

    //非堆内存溢出：-XX:MetespaceSize=32M -XX:MaxMetaspaceSize=32M
    public static List< Class<?>> createClass(){
        //类持有
        List<Class<?>> list = new ArrayList<>();
        //生成不同地方类
        for( int i = 0 ; i < 10000000 ;i++ ){
//            ClassWriter classWriter = new ClassWriter(  1  );
//            classWriter.visit();

        }

        return list;
    }

    public static void main(String[] args) {
        while ( true ){
            num++;

        }
    }
}
