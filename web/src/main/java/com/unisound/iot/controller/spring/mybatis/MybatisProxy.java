package com.unisound.iot.controller.spring.mybatis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

public class MybatisProxy {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext( Appconfig.class);



        //模拟Mybatis
        Class[] classes = new Class[]{ CityDao.class };
        CityDao cityDao = ( CityDao ) Proxy.newProxyInstance( MybatisProxy.class.getClassLoader(),
                classes , new MybatisInvocationHandler() );

        cityDao.query();
        //但是cityDao 生成的对象是怎么样交给spring管理的？？？
        //注册一个bean
        ac.getBeanFactory().registerSingleton( "cityDao" , cityDao );
        //TODO mybatis 没有采用上面的方式
        ac.getBean( TestService.class );
        //

    }



















}
