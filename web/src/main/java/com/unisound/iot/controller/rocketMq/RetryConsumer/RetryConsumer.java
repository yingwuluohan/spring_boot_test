package com.unisound.iot.controller.rocketMq.RetryConsumer;

import org.apache.rocketmq.broker.mqtrace.ConsumeMessageContext;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.message.Message;

/**
 * 消息重试
 */
public class RetryConsumer implements MessageListener {

    public  void consume(Message message , ConsumeMessageContext context ){

    }

}
