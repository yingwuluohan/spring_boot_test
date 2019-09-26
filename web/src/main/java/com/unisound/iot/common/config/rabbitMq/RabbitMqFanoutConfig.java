package com.unisound.iot.common.config.rabbitMq;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * rabbitMq配置
 * </p>
 *
 * @author yinguanqun
 * @since 2018-05-18
 */
@Configuration
public class RabbitMqFanoutConfig {


  /**
   * fanout 路由器
   * @return
   */
  @Bean(name="platform-fanout-queue1")
  public Queue queue1(){
    System.out.println( "MQ queue 实例化platform-fanout-queue1 " );

    return new Queue("platform-fanout-queue1");
  }

  @Bean(name="platform-fanout-queue2")
  public Queue queue2(){
    System.out.println( "MQ queue 实例化platform-fanout-queue2-- " );

    return new Queue("platform-fanout-queue2");
  }

  @Bean(name="platform-fanout-queue3")
  public Queue queue3(){
    return new Queue("platform-fanout-queue3");
  }

  @Bean(name="platform-fanout-exchange")
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange("platform-fanout-exchange");
  }





  @Bean
  Binding bindQueueToExchange(@Qualifier("platform-fanout-queue1") Queue platFormQueue,
                              @Qualifier("platform-fanout-exchange")FanoutExchange platFormDirectExchange){
    System.out.println( "fanout init queue1 " );
    return BindingBuilder.bind( platFormQueue).to( platFormDirectExchange );
  }

  @Bean
  Binding bindQueue2ToExchange(@Qualifier("platform-fanout-queue2") Queue platFormQueue,
                              @Qualifier("platform-fanout-exchange")FanoutExchange platFormDirectExchange){
    return BindingBuilder.bind( platFormQueue).to( platFormDirectExchange );
  }
  @Bean
  Binding bindQueue3ToExchange(@Qualifier("platform-fanout-queue3") Queue platFormQueue,
                              @Qualifier("platform-fanout-exchange")FanoutExchange platFormDirectExchange){
    return BindingBuilder.bind( platFormQueue).to( platFormDirectExchange );
  }




}
