package com.unisound.iot.controller.spring.transaction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;


@Component
public class CompentInteceptor implements BeanPostProcessor , InstantiationAwareBeanPostProcessor {


    public Object postProcessBeforeInstantiation(Class<?> beanClass , String beanName ) throws BeansException{
        if( "compent".equals( beanName )){
            System.out.println( beanName + "实例化之前" );
        }
        return beanClass;
    }


    public boolean postProcessAfterInstantiaion( Object bean , String beanName )throws BeansException{
        if( "compent".equals( beanName )){
            System.out.println( beanName + "实例化之后--" );
        }
        return true;
    }


}
