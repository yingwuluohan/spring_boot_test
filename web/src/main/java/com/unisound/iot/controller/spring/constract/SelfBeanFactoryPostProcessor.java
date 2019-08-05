package com.unisound.iot.controller.spring.constract;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @Created by yingwuluohan on 2019/7/15.
 * @Company 北京云知声技术有限公司
 */

//@Component
public class SelfBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition service = (GenericBeanDefinition) beanFactory.getBeanDefinition( "service");
//
//        //加载,通过构造方法注入（ 4种注入模型 ）
        service.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR );
//        //怎样指定加载具体某个构造方法？？如下
        service.getConstructorArgumentValues().addGenericArgumentValue( "name" );

    }
}
