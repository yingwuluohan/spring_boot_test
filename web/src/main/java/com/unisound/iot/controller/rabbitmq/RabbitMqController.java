package com.unisound.iot.controller.rabbitmq;

import com.unisound.iot.service.rabbitmq.RabbitMqProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
}
