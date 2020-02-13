package com.unisound.iot.controller.rocketMq.AsyncProducer;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息 :不会等待消费者回传结果 ，但可以接收回传结果，当有结果时，在回调函数中处理
 * 场景：对发送消息效率高，不可以有延迟，但返回时间要求不高的场景比较适用
 *
 */
public class AsyncProducer {
    public static void main(String[] args) throws RemotingException, InterruptedException {
        //实例化消息生产者
        DefaultMQProducer producer = new DefaultMQProducer( "unique_group_name" );
        //设置nameServer地址
        producer.setNamesrvAddr( "192.168.12.123:9876" );
        //启动producer实例
        try {
            producer.start();
            //
            producer.setRetryTimesWhenSendAsyncFailed( 0 );
            for ( int i = 0 ;i < 100 ;i++ ){
                final int index =i ;
                //创建消息，指定topic ，tag和消息体
                try {
                    Message msg = new Message( "TopicTest" ,
                            "Tags",
                            "orderId12",
                            "hello world ".getBytes(RemotingHelper.DEFAULT_CHARSET ));
                    //发送异步消息，结果是通过回调获取的，sendCallback 接收异步返回结果的回调
                    producer.send(msg, new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            System.out.printf( "%-10d OK %s %n" ,index ,sendResult.getMsgId() );
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            System.out.println( "结果异常" );
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                TimeUnit.SECONDS.sleep( 2 );

            }
        } catch (MQClientException e) {
            e.printStackTrace();
        }


    }

}
