package com.unisound.iot.controller.spring.mybatis;

import org.mybatis.spring.SqlSessionFactoryBean;

import javax.sql.DataSource;

//@Configuration
//@ComponentScan("com.unisound.iot.controller.spring")
//@LubanScan
//@ImportResource("classpath:spring.xml")//spring5 新特性
public class Appconfig {



//    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource ){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource( dataSource );

        return  factoryBean;
    }











}
