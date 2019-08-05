package com.unisound.iot.controller.classLoader;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Created by yingwuluohan on 2019/6/24.
 * @Company 北京云知声技术有限公司
 */
public class HotFix {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, MalformedURLException, ClassNotFoundException {

        classLoader();

    }

    static void classLoader()throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //自定义类加载器，JVM加载class文件工具，支持通过文件路径
        URLClassLoader classLoader = null;
        //class文件的位置
        URL url = new URL( "/workplace/spring_boot_operation/web/src/main/java/com/unisound/iot/controller/classLoader" );
        //双亲委派，当前类的父加载器
        classLoader = new URLClassLoader( new URL[]{ url},HotFix.class.getClassLoader() );
        //类加载流程
        Class classes = classLoader.loadClass( "HotFix.java" );
        Object instance = classes.newInstance();

        Object result = classes.getMethod( "getLoadername" ).invoke( instance );

        System.out.println( "getLoadername获取的值" + result );
        //销毁，卸载


        /**
         * 1. 加载class文件到内存
         * 2. 校验
         * 3.准备内存，JVM模型
         * 4.解析:接口，方法，字段
         * 5.初始化：静态变量，静态代码块
         * 6.使用：new 对象
         * 7.销毁
         *
         * 同一个类名，同一个类加载器加载的代表同一个类
         *
         */
    }



}
