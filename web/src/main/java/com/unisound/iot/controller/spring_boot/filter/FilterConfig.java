package com.unisound.iot.controller.spring_boot.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    /**当前策略api */
    private final String polic_current_url = "/services/chargePolicyDetail/getPolicCurrentTime";

    /**
     * 注册过滤器
     * @return FilterRegistrationBean
     * 以”/’开头和以”/*”结尾的是用来做路径映射的
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(replaceStreamFilter());
        registration.addUrlPatterns( polic_current_url );
        registration.setName("operationFilter");
        return registration;
    }
    /**
     * 实例化StreamFilter
     * @return Filter
     */
    @Bean(name = "replaceStreamFilter")
    public Filter replaceStreamFilter() {
        return new ReplaceStreamFilter();
    }
}
