package com.unisound.iot.common.config.rabbitMq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;



@Component
public class MessageRecevier {

    private static final Logger logger = LoggerFactory.getLogger(MessageRecevier.class);
//    @Resource
//    private PayFlowMapper payFlowMapper;

    @RabbitListener(bindings =
    @QueueBinding( value = @Queue(value = "busi.shopping.order.status", autoDelete = "false"),
            exchange = @Exchange(value = "shopping-direct-exchange", type = ExchangeTypes.DIRECT),
            key = "busi.shopping.order.status"))
    @RabbitHandler
    public void process(Message message, Channel channel) {
        try {
            String queueName = message.getMessageProperties().getConsumerQueue();
            String messageStr = new String(message.getBody());
            if (StringUtils.isBlank(messageStr)) {
                logger.info("消息为空，无处理");
                return;
            }
            logger.info(queueName + ":" + messageStr);
            JSONObject messageJson = JSONObject.parseObject(messageStr);
            if (StringUtils.isBlank( "" )){
                logger.info("订单为空，无处理");
                return;
            }


            // 第一个参数为消息id，由生产者生成，第二个参数为确认标识：false只确认当前一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            logger.error("消费消息处理错误:"+ e.getMessage(), e);
            /*try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            } catch (IOException e1) {
                logger.error("消息处理异常,requeqe错误"+ e1.getMessage(), e1);
            }*/
        }
    }

}
