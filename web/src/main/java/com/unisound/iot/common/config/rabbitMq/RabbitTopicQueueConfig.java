package com.unisound.iot.common.config.rabbitMq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company 北京云知声技术有限公司
 */

@Configuration
public class RabbitTopicQueueConfig {

    @Bean(name= "topic-first-queue")
    public Queue getTopicFirstQueue(){
        return new Queue( "topic-first-queue" );
    }


    @Bean(name= "topic-secend-queue")
    public Queue getTopicSecendQueue(){
        return new Queue( "topic-secend-queue" );
    }


    @Bean(name= "topic-third-queue")
    public Queue getTopicThirdQueue(){
        return new Queue( "topic-third-queue" );
    }



}
