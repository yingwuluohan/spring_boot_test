package com.unisound.iot.controller.rocketMq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 事务消息的生产者
 *
 */
public class TransacProducer {

    public static void main(String[] args) throws RemotingException, InterruptedException, MQClientException {
        //实例化消息生产者
        TransactionMQProducer producer = new TransactionMQProducer( "unique_group_name" );
        //设置nameServer地址
        producer.setNamesrvAddr( "192.168.12.123:9876;192.168.12.125:9876" );
        //事务监听器
        producer.setTransactionListener(new TransactionListener() {
            //执行本地事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if(StringUtils.endsWith( "TAGA" , message.getTags() )){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if(StringUtils.endsWith( "TAGB" , message.getTags() )){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else {
                    return LocalTransactionState.UNKNOW;
                }

            }
            //如果没有对消息提交或者回滚，该mq进行消息事务状态的回查
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println( "消息的tag：" + messageExt.getTags() );
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        //启动producer实例
          producer.start();
        //构建消息对象
        for ( int i = 0 ;i < 10;i++ ){
            Message message = new Message( "transctopic" ,"order" , i+"" ,"hellow world".getBytes());
            SendResult result = producer.sendMessageInTransaction( message , null );
            SendStatus status = result.getSendStatus();
            System.out.println( "" + status );

            TimeUnit.SECONDS.sleep( 2 );
        }


    }

}
