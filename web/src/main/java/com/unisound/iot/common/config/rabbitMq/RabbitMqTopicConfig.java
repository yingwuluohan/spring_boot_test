package com.unisound.iot.common.config.rabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created by yingwuluohan on 2019/4/11.
 */
@Configuration
public class RabbitMqTopicConfig {


    /** 路由键可以匹配所有以 routing-key 开头的队列  */
    public static final String routingKeyLike = "routing-key.#";

    /** 路由键可以匹配所有以  topic 结尾的队列  */
    public static final String likeTopic = "#.topic";


    @Bean( name = "topic-platform-exchange")
    TopicExchange getTopicExchange(){
        return new TopicExchange( "topic-platform-exchange" );

    }


    /**
     * 在topic-platform-exchange 中，路由键以routing-key开头的队列都将被匹配到
     * @param topicFirstQueue
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingQueueFirstToTopicExchange(
            @Qualifier("topic-first-queue") Queue topicFirstQueue,
            @Qualifier("topic-platform-exchange") TopicExchange topicExchange
    ){
        return BindingBuilder.bind( topicFirstQueue ).to( topicExchange ).with(routingKeyLike);
    }


    @Bean
    Binding bindingQueueSencendToTopicExchange(
            @Qualifier("topic-secend-queue") Queue topicSencendQueue,
            @Qualifier("topic-platform-exchange") TopicExchange topicExchange
    ){
        return BindingBuilder.bind( topicSencendQueue ).to( topicExchange ).with(likeTopic);
    }

    /** 在此exchange种，任何 队列都将被匹配到 */
    @Bean
    Binding bindingQueueThirdToTopicExchange(
            @Qualifier("topic-third-queue") Queue topicThirdQueue,
            @Qualifier("topic-platform-exchange") TopicExchange topicExchange
    ){
        return BindingBuilder.bind( topicThirdQueue ).to( topicExchange ).with("#");
    }

}
