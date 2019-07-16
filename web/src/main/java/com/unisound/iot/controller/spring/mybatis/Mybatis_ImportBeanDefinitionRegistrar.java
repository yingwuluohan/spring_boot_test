package com.unisound.iot.controller.spring.mybatis;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class Mybatis_ImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    //Mybatis的做法是扫描传入的包名下所有的类

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        //给出一个包名 得到所有类
//        for()

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition( SpringFactory.class ) ;
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition)builder.getBeanDefinition();
        registry.registerBeanDefinition( "cityDao" ,genericBeanDefinition );

        genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue( "com.ll.cityDao" );
        genericBeanDefinition.setAutowireMode( 3 );// 通过构造方法注入

    }
}
