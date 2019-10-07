package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.EventHandler;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;


public class MessageHandler implements EventHandler< ObjectEvent> {






    @Override
    public void onEvent(ObjectEvent objectEvent, long l, boolean b) throws Exception {
        System.out.println( objectEvent.getObject() );
    }
}
