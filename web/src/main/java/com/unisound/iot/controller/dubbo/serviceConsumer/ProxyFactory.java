package com.unisound.iot.controller.dubbo.serviceConsumer;

import com.unisound.iot.controller.dubbo.frame.Invocation;
import com.unisound.iot.controller.dubbo.frame.LoadBalance;
import com.unisound.iot.controller.dubbo.frame.Url;
import com.unisound.iot.controller.dubbo.register.RemoteMapRegister;
import com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 * Consumer 的改进类
 * 通过spring getBean的方法获取接口的对象实例
 */
public class ProxyFactory {


    @SuppressWarnings( "unchecked")
    public static <T>T getProxy( Class interfaceClass ){

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        HttpClient httpClient = new HttpClient();
                        Invocation invocation = new Invocation(
                                interfaceClass.getName() ,method.getName() ,
                                method.getParameterTypes() , args );

                        List<Url > list = RemoteMapRegister.get( interfaceClass.getName());
                        Url url = LoadBalance.random( list );



                        httpClient.send( url.getHost() ,8080 ,invocation );
                        //上面地址 怎么动态获取？
                        return null;
                    }
                });

    }











}
