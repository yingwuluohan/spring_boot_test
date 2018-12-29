package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;

import java.nio.ByteBuffer;

public class ObjectEventProducerWithTranslator {

    private final RingBuffer<ObjectEvent> ringBuffer;

    public ObjectEventProducerWithTranslator(RingBuffer<ObjectEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<ObjectEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<ObjectEvent, ByteBuffer>()
            {
                public void translateTo(ObjectEvent event, long sequence, ByteBuffer bb)
                {
                    System.out.println( "" + event.getObject() + ", sequence:" + sequence );
                    event.setObject( bb.getLong(0) );
                }
            };

    public void product(ByteBuffer bb)
    {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
