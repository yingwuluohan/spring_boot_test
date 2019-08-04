package com.unisound.iot.controller.dubbo.serviceConsumer;

import com.unisound.iot.controller.dubbo.ServiceProvider.HellowService;
import com.unisound.iot.controller.dubbo.frame.Invocation;
import com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol.HttpClient;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company 北京云知声技术有限公司
 */
public class Consumer {

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();
        Invocation invocation = new Invocation(HellowService.class.getName() ,
                "" , new Class[]{},new Object[]{});
        httpClient.send( "localhost" ,8080 ,invocation );

        //TODO 上面的调用方式很不灵活方变，dubbo本身通过代理的形式获得接口对应的对象实例
        //TODO 这样就可以通过spring getBean的方法获取接口的对象实例
        //TODO 动态代理改进后
        HellowService hellowService = ProxyFactory.getProxy(HellowService.class );
        hellowService.sayHellow( "hwllow" );


    }





}
