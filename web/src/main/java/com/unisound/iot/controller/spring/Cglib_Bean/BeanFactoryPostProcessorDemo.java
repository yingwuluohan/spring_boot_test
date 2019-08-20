package com.unisound.iot.controller.spring.Cglib_Bean;

import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanFactoryPostProcessorDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppconfigTest.class );
//
//        System.out.println( ac.getBean( ));
        // 要代理哪个类就继承那个类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
//        enhancer.setUseFactory(false);
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        // 过滤方法 ，不能每次都去new ,代理的本质就是对一个对象，方法进行增强 ,对方法进行拦截 ，即需要一个拦截器
//        enhancer.setCallbackFilter(CALLBACK_FILTER);
        enhancer.setCallback( new LubanMethodInterceptor());
        UserService userService = ( UserService ) enhancer.create();
//        enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
        userService.query();// 如果拦截器中没有调用父类的invoke ，则userService query 不会被执行
        System.out.println( );
    }























}
