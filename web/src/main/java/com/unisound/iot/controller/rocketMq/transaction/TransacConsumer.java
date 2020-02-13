package com.unisound.iot.controller.rocketMq.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务消费者
 */
public class TransacConsumer {

    public static void main(String[] args) throws MQClientException {
        //主动推的模式
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer( "group1" );
        //指定nameServer地址
        consumer.setNamesrvAddr( "192.168.23.123:9876;192.168.23.125:9876" );
        //订阅主题
        consumer.subscribe( "transctopic" ,"*" );

        //设置回调函数，处理消息
        consumer.registerMessageListener( ( MessageListenerConcurrently)( mags , contex ) ->{
            for( MessageExt msg : mags ){
                System.out.println( "线程:" + Thread.currentThread().getName() + "" + new String( msg.getBody() ));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动
        consumer.start();

    }

}
