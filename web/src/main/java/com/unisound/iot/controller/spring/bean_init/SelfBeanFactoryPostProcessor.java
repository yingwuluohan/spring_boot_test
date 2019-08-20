package com.unisound.iot.controller.spring.bean_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @Created by yingwuluohan on 2019/7/28.
 * @Company
 *
 *
 * 1.	BeanFactoryPostProcessor 接口
        通过这个接口用户可以干预spring 实例化bean的过程

 */
public class SelfBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        RootBeanDefinition beanDefinition = (RootBeanDefinition)beanFactory.getBeanDefinition("testBeanService");
        beanDefinition.setBeanClass( TestBeanService.class );



    }




}
