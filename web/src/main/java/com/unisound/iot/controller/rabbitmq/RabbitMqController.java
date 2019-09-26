package com.unisound.iot.controller.rabbitmq;

import com.rabbitmq.client.*;
import com.unisound.iot.service.rabbitmq.RabbitMqProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Created by yingwuluohan on 2019/4/11.
 * @Company 北京云知声技术有限公司
 */

@RestController
@RequestMapping("mq/")
public class RabbitMqController {


    @Autowired
    private RabbitMqProductService rabbitMqService;

    /**
     * fanout类型发送消息：mq/send?param=fanouttest
     * @param request
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="send",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendFanoutInfoMq(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {

        rabbitMqService.fanoutExchangeSendInfo( param );
        return null;
    }

    /**
     * direct 类型发送：
     * @param request
     * @param param
     * @return
     * mq/send/direct?param=directtest
     *
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="send/direct",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendDirectInfoMq(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {

        rabbitMqService.directExchangeSendInfo( param );
        return null;
    }
    /** mq/send/topic?param=topictest  */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="send/topic",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendTopicFirstInfoMq(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {

        rabbitMqService.topicFirstExchangeSendInfo( param );
        return null;
    }

    /**mq/send/topicSecend?param=topictest*/
    @SuppressWarnings("unchecked")
    @RequestMapping(value="send/topicSecend",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendTopicSecendInfoMq(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {

        rabbitMqService.topicSecendExchangeSendInfo( param );
        return null;
    }


    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.20.222.62");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //交换机声明（参数为：交换机名称；交换机类型）
            channel.exchangeDeclare("fanoutLogs", BuiltinExchangeType.FANOUT);
            //获取一个临时队列
            String queueName = channel.queueDeclare().getQueue();
            //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
            channel.queueBind(queueName, "fanoutLogs", "");

            System.out.println("********Waiting for messages********");

            //这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body, "UTF-8");
                    System.out.println("received:" + message);
                }
            };

            //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
            channel.basicConsume(queueName , true , consumer);
            //这里不能关闭连接，调用了消费方法后，消费者会一直连接着rabbitMQ等待消费
//            consumer.handleDelivery( );
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
