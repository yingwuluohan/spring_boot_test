package com.unisound.iot.core.product;

import com.unisound.iot.common.modle.message.Container;
import com.unisound.iot.common.modle.message.Message;
import org.springframework.stereotype.Service;


@Service
public class ProductMessage {

//    @Autowired
//    private Container container;

    public void productMessage(Message message ){
        Container.getInstance().putMessage( message );

    }










}
