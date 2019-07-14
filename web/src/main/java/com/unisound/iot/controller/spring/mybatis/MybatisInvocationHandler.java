package com.unisound.iot.controller.spring.mybatis;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MybatisInvocationHandler implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println( "Mybatis 代理操作业务------------------" + method.getAnnotation(Select.class).value()[ 0 ]);
        return null;
    }
}
