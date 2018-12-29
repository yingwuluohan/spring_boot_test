package com.unisound.iot.service.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;

public class ObjectEventFactory implements EventFactory<ObjectEvent>
{

    @Override
    public ObjectEvent newInstance() {
        return new ObjectEvent();
    }
}
