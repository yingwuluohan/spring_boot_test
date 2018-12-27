package com.unisound.iot.controller.message;


import com.unisound.iot.common.modle.message.Message;
import com.unisound.iot.common.vo.ItemVo;
import com.unisound.iot.core.consum.ConsumMessageService;
import com.unisound.iot.core.product.ProductMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("message/")
public class MessageController {


    @Autowired
    private ConsumMessageService consumMessageService;

    @Autowired
    private ProductMessage productMessage;

    @SuppressWarnings("unchecked")
    @RequestMapping(value="consum",method = {RequestMethod.GET, RequestMethod.POST})
    public String consumMessage(HttpServletRequest request, @ModelAttribute ItemVo itemVo ){
        consumMessageService.consumMessage();
        return "ok";
    }
    /**
     * message/product?num=2000
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="product",method = {RequestMethod.GET, RequestMethod.POST})
    public String productMessage(HttpServletRequest request, @RequestParam(value = "num", required = true , defaultValue = "10")Integer num){
        product( num );
        return "ok";
    }


    public void product( int num ){
        for( int i = 0 ;i < num ; i++ ){
            Message message = new Message();
            message.setId( Long.valueOf( i ));
            message.setContent( "Content" + i );

            productMessage.productMessage(message );
        }


    }




}
