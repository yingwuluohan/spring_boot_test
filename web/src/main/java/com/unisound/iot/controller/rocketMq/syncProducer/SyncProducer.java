package com.unisound.iot.controller.rocketMq.syncProducer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息 :eg : 消息通知，短信通知
 * 可靠性高
 */
public class SyncProducer {

    public static void main(String[] args) throws UnsupportedEncodingException {
        //
        DefaultMQProducer producer = new DefaultMQProducer();
        //设置nameServer地址
        producer.setNamesrvAddr( "localhost:9876" );
        //启动producer实例
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        for ( int i =0 ;i< 100;i++ ){
            //创建消息，并制定topic ，tag 和消息体
            Message msg = new Message( "TopicTest" ,"TagA",
                    ("Hellow world "+i ).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息到broker
            try {
                SendResult result = producer.send( msg );
                //查看返回消息
                System.out.println( "消息状态" + result.getSendStatus()  );
                System.out.println( "消息id:" + result.getMsgId()   );
                System.out.println( "消息队列id:" + result.getMessageQueue().getQueueId()  );
                //线程sleep
                TimeUnit.SECONDS.sleep( 2 );

            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //关闭producer 实例
        producer.shutdown();
    }


}
