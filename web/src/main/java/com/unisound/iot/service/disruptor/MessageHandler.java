package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.unisound.iot.common.modle.Message;

public class MessageHandler implements EventHandler<Message> {

    private Disruptor disruptor;


    @Override
    public void onEvent(Message message, long l, boolean b) throws Exception {
        String content = message.getContent();
        System.out.println( "处理器接收到内容是:" + content );
    }




}
