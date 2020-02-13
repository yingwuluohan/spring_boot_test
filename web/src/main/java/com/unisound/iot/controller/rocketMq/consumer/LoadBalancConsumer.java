package com.unisound.iot.controller.rocketMq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 负载均衡消费模式
 * 上图1 和 2 两个消费者共同承担消费消息Message a ,b,c 的任务，即为负载均衡
 */
public class LoadBalancConsumer {


    public static void main(String[] args) throws MQClientException {
        //实例化
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer( "group1" );
        //指定nameServer 地址
        consumer.setNamesrvAddr( "192.168.25.123:9876" );
        //订阅topic
        try {
            consumer.subscribe( "Test" ,"*" );
            //TODO 设置负载均衡模式消费( 默认模式就是负载均衡 )
            consumer.setMessageModel(MessageModel.CLUSTERING );
            //注册函数回调
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    System.out.printf( "%s Receive new mag: %s%n" + Thread.currentThread().getName() , msgs);

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //启动
        consumer.start();

    }














}
