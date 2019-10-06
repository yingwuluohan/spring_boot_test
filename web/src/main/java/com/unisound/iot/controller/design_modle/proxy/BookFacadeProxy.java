package com.unisound.iot.controller.design_modle.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/** 
* @date 创建时间：2015年12月31日 下午6:10:24
* @version 1.0 
* @parameter   
* @return  
*/
public class BookFacadeProxy implements InvocationHandler {  
    private Object target;  
    /** 
     * 绑定委托对象并返回一个代理类 
     * @param target 
     * @return R
     */  
    public Object bind(Object target) {  
        this.target = target;  
        //取得代理对象  
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),  
                target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)  
    }  
  
    @Override  
    /** 
     * 调用方法 
     */  
    public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {  
        Object result=null;  
        System.out.println("事物开始");  
        //执行方法  
        System.out.println( method.getDeclaringClass().getName());
        System.out.println( "方法名称:" + method.getName());
        result=method.invoke(target, args);  
        System.out.println("事物结束");  
        return result;  
    }  
  
} 
