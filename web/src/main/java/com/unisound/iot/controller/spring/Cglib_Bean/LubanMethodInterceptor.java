package com.unisound.iot.controller.spring.Cglib_Bean;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LubanMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println( " intercopor cglib " );
        return null;
//        return methodProxy.invokeSuper(  o  , objects );//执行模板目标方法



    }
}
