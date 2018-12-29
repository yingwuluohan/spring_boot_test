package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;

import java.nio.ByteBuffer;

public class ObjectEventProducer {
    private final RingBuffer<ObjectEvent> ringBuffer;

    public ObjectEventProducer(RingBuffer<ObjectEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void product(ByteBuffer bb)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            ObjectEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setObject(bb.getLong(0));  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}
