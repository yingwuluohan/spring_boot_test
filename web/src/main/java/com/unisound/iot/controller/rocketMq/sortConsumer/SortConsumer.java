package com.unisound.iot.controller.rocketMq.sortConsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 同步 顺序消息的消费
 */
public class SortConsumer {
    public static void main(String[] args) throws MQClientException {
        //topic 保证跟生产的一致
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer( "group1" );
        //指定nameServer地址
        consumer.setNamesrvAddr( "192.168.23.123:9876" );
        //订阅主题
        consumer.subscribe( "orderTopic" ,"*" );

        //TODO 注册消息监听器 MessageListenerOrderly :对于一个队列的消息就采用一个线程去处理
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
                for( MessageExt msg : list ){

                    System.out.println( "消息：" + new String ( msg.getBody() ));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();

        System.out.println( "消费者启动" );
    }
}
