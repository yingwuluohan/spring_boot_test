package com.unisound.iot.controller.spring.mybatis;


import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
@Import(Mybatis_ImportBeanDefinitionRegistrar.class)
public @interface LubanScan {





}
