package com.unisound.iot.controller.spring.mybatis;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;


//@Component
public class SpringFactory implements FactoryBean {
    /**
     * 第二版：
     */
    Class mapperInterface;

    public SpringFactory(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
    /**第二版 ，必须要有个无参的*/
    public SpringFactory( ) {
    }

    @Override
    public Object getObject() throws Exception {


        /**
         * 第一版：
         */
//        Class[] classes = new Class[]{ CityDao.class };
//        CityDao cityDao = ( CityDao ) Proxy.newProxyInstance( SpringFactory.class.getClassLoader(),
//                classes , new MybatisInvocationHandler() );
//        return cityDao;
        /**
         * 第二版：
         */
        Class[] classes = new Class[]{ mapperInterface };
        Object object = Proxy.newProxyInstance( SpringFactory.class.getClassLoader(),
                classes , new MybatisInvocationHandler() );
        return object;


    }

    @Override
    public Class<?> getObjectType() {

        return mapperInterface;
    }


    /**
     * 第二版：
     */

















}
