package com.unisound.iot.common.config.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created by yingwuluohan on 2019/4/11.
 */

@Configuration
public class RabbitMqDirectConfig {

    public static final String routingKey = "routingKey-direct";

    /**
     * 注入队列实例
     * @return
     */
    @Bean( name= "direct-queue-platform" )
    public Queue getQueue(){
        return new Queue( "direct-queue-platform" );
    }


    /**
     * direct 路由器 ,需要绑定路由键：routingKey
     * @param
     * @param
     * @return
     */
    @Bean( name = "direct-test-exchange")
    DirectExchange getDirectExchange(){
        return new DirectExchange( "direct-test-exchange" );
    }


    @Bean
    Binding bingdingQueueOneToDirectExchange(
            @Qualifier("direct-queue-platform") Queue directQueuePlatform,
            @Qualifier("direct-test-exchange")DirectExchange directExchange ){

        return BindingBuilder.bind(directQueuePlatform).to( directExchange ).with( routingKey );
    }









}
