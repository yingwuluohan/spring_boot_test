package com.unisound.iot.service.rabbitmq;

import com.unisound.iot.common.config.rabbitMq.RabbitMqDirectConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company fn
 */
@Service
public class RabbitMqProductService {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void fanoutExchangeSendInfo(String message){
        /**
         * 第一个参数表示交换机，
         * 第二个参数表示 routing key
         * */
        try{

            amqpTemplate.convertAndSend( "platform-fanout-exchange" , "","fanout-send:"+message );
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }


    public void directExchangeSendInfo(String message){

        amqpTemplate.convertAndSend( "direct-test-exchange" , RabbitMqDirectConfig.routingKey,"direct-send:"+message );

    }

    public void topicFirstExchangeSendInfo(String message){

        amqpTemplate.convertAndSend( "topic-platform-exchange" , "routing-key.test","topic-send:"+message );

    }
    public void topicSecendExchangeSendInfo(String message){

        amqpTemplate.convertAndSend( "topic-platform-exchange" , "test.topic" ,"topic-send:"+message );

    }


}
