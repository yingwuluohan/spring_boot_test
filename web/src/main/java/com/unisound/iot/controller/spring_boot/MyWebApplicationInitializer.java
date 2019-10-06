package com.unisound.iot.controller.spring_boot;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 以下就完成了SpringMVC的配置
 * 不再需要xml文件的配置
 * servlet 3.0 功能
 * 步骤：初始化servlet 容器
 * 2. 加载监听器
 *
 *
 */

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    //onStartup 怎样被调用到？？
    //TODO
    @Override
    public void onStartup(ServletContext servletCxt) {
        //TOOD  servletCxt就是web容器
        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        //TODO 下面的代码+ @ComponentScan 就完成了替换加载原applicationContext.xml 作用的功能
        ac.register(AppConfig.class);
        ac.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }












}
