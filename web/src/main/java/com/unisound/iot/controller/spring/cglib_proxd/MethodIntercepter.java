package com.unisound.iot.controller.spring.cglib_proxd;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Created by yingwuluohan on 2019/7/27.
 * @Company
 *
 * spring 也是通过方法拦截器来增强注解的方法功能
 */
public class MethodIntercepter implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println( "MethodIntercepter " );

        // 执行父类方法，即目标方法 回调
        //TODO 如果不调用methodProxy.invokeSuper 目标方法则 原方法不会被执行
        return methodProxy.invokeSuper( o , objects );
    }
}
