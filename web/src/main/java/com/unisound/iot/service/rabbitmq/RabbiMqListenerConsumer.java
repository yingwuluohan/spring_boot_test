package com.unisound.iot.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company
 */
@Component
public class RabbiMqListenerConsumer {

    /** fanout 类型的路由 ，凡是绑定到exchange的 队列都会接收到消息 */
    @RabbitListener(queues = "platform-fanout-queue1")
    public void fanoutQueue1Listener(String queue ){
        System.out.println( "fanout队列:1 监听接收：" + queue );
    }
    @RabbitListener(queues = "platform-fanout-queue2")
    public void fanoutQueue2Listener(String queue ){
        System.out.println( "fanout队列:2 监听接收：" + queue );
    }
    @RabbitListener(queues = "platform-fanout-queue3")
    public void fanoutQueue3Listener(String queue ){
        System.out.println( "fanout队列:3 监听接收：" + queue );
    }


    @RabbitListener(queues = "direct-queue-platform")
    public void directQueueListener(String queue ){

        System.out.println( "direct队列监听接收：" + queue );

    }


    @RabbitListener(queues = "topic-first-queue")
    public void topicQueueFirstListener(String queue ){

        System.out.println( "topic-first-queue队列监听接收：" + queue );
    }

    @RabbitListener(queues = "topic-secend-queue")
    public void topicQueueSecendListener(String queue ){

        System.out.println( "topic-secend-queue队列监听接收：" + queue );
    }
    @RabbitListener(queues = "topic-third-queue")
    public void topicQueueThirdListener(String queue ){

        System.out.println( "topic-third-queue队列监听接收：" + queue );
    }



    @RabbitListener( bindings = @QueueBinding(
                    //1.test.demo.send:队列名,2.true:是否长期有效,3.false:是否自动删除
                    value = @Queue(value = "topic-first-queue", durable = "true", autoDelete = "false"),
                    //1.default.topic交换器名称(默认值),2.true:是否长期有效,3.topic:类型是topic
                    exchange = @Exchange(value = "topic-platform-exchange", durable = "true", type = "topic", autoDelete = "false"),
                    //test2.send:路由的名称,ProducerConfig 里面 绑定的路由名称(xxxx.to(exchange).with("test2.send")))
                    key = "routing-key.#")  )
    public void topicQueueListener( String info ){
        System.out.println( "topic-first-queue 队列接收到：" + info );
    }













}
