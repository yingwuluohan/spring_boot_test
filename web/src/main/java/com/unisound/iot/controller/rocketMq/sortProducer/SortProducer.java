package com.unisound.iot.controller.rocketMq.sortProducer;

import com.unisound.iot.common.modle.Order;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * 保证顺序消息的生产者
 */
public class SortProducer {
    public static void main(String[] args) throws RemotingException, InterruptedException {
        //实例化消息生产者
        DefaultMQProducer producer = new DefaultMQProducer( "unique_group_name" );
        //设置nameServer地址
        producer.setNamesrvAddr( "192.168.12.123:9876" );
        //启动producer实例
        try {
            producer.start();
            //构建消息集合
            List<Order> orderStepList = Order.builds();
            producer.setRetryTimesWhenSendAsyncFailed( 0 );
            int num =0;
            for (Order order : orderStepList ){
                num++;
                //构建消息
                String body = order +"";
                Message message = new Message( "orderTopic" ,"order" , num+"" ,body.getBytes());
                /**
                 * 参数1:消息对象
                 * 2：队列选择器
                 * 3：选择对了的业务标识 ：订单ID
                 * MessageQueueSelector ： 消息选择器
                 */
                producer.send(message, new MessageQueueSelector() {
                    /**
                     * 参数：队列集合
                     * 参数2：消息对象
                     * 参数3：业务标识参数
                     * 就是订单ID 跟队列个数取模的长度 让同一个订单的业务消息都发送到同一个队列
                     */
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object object) {
                        Integer orderId = ( Integer ) object;
                        Integer modle = orderId % list.size();
                        return list.get( ( int ) modle );
                    }
                } , order.getId() );//TODO 此处的参数同 （方法：select） 最后一个参数对应
            }
        } catch (MQClientException | MQBrokerException e) {
            e.printStackTrace();
        }


    }


}
