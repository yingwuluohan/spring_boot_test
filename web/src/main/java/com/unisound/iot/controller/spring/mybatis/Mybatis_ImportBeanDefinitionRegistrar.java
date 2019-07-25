package com.unisound.iot.controller.spring.mybatis;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


/**
 * spring容器，一系列组件
 */
public class Mybatis_ImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    //Mybatis的做法是扫描传入的包名下所有的类

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        //给出一个包名 得到所有类
//        for()

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition( SpringFactory.class ) ;

        /**
         * 拿到了bean的
         * 是不是懒加载，bean是不是抽象类，
         * 如果SpringFactory 没有构造方法
         */
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();


        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition)builder.getBeanDefinition();
        registry.registerBeanDefinition( "cityDao" ,genericBeanDefinition );
        // 先获取构造方法，再传给构造方法具体的类 ，这样就可以实例化具体的bean了
        genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue( "com.ll.cityDao" );
        genericBeanDefinition.setAutowireMode( 3 );// 通过构造方法注入

    }
}
