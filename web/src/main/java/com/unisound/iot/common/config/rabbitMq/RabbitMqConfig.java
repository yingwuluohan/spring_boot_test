package com.unisound.iot.common.config.rabbitMq;


import org.springframework.amqp.core.Queue;
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
public class RabbitMqConfig {

  @Bean
    public Queue queue(){
      return new Queue("shopping-direct-exchange");
  }

  /*@Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange("shopping-direct-exchange");
  }
*/
}
