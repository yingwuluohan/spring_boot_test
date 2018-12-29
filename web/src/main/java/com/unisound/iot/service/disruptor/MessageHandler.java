package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;

public class MessageHandler implements EventHandler<ObjectEvent> {

    private Disruptor disruptor;


    @Override
    public void onEvent(ObjectEvent message, long sequence, boolean endOfBatch) throws Exception {
        Object content = message.getObject();
       // Thread.sleep( 100 );
        System.out.println("消息的消费线程------ :"+Thread.currentThread().getName()+  " Event: value=" + content +",sequence="+sequence+",endOfBatch="+endOfBatch);
    }




}
