package com.unisound.iot.controller.rocketMq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws MQClientException {
        //主动推的模式
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer( "group1" );
        //指定nameServer地址
        consumer.setNamesrvAddr( "192.168.23.123:9876" );
        //订阅主题
        try {
            consumer.subscribe( "base" ,"tag1" );
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            //接收消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for( MessageExt msg: msgs ) {
                    System.out.println("接收: " + msg.getBody() );
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }

}
