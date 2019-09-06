package com.unisound.iot.controller.spring.Cglib_Bean;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //保证spring bean的生命周期和作用域
@ComponentScan("com.unisound.iot.controller.spring")
//@LubanScan
public class AppconfigTest {


    @Bean
    public UserService userService(){
        return new UserService();
    }

   @Bean
    public OrderService userOrderService(){
        //当加上 @Configuration 后 UserService 的构造方法只会被实例化一次
       // 即 AppconfigTest 被spring cglib 代理了
       userService();

       return new OrderService();
   }










}
