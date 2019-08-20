package com.unisound.iot.controller.spring.cglib_proxd;

import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @Created by yingwuluohan on 2019/7/27.
  */
public class EnhansTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass( ProxyService.class );
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);

        enhancer.setCallback( new MethodIntercepter() );
//        enhancer.setStrategy( new BeanFactoryAwareGeneratorStrategy() );
        ProxyService service = ( ProxyService) enhancer.create();
        service.query();

    }
}
