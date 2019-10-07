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

    /**
     * ByteBuffer :
     * @param bb
     */
    public void product(ByteBuffer bb)
    {
        //next 获取下一个事件 的下标
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            //获取一个空余的位置
            ObjectEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            //ringbuffer里面在初始化时已经实现存放好了ObjectEvent类型的对象,此时只需要赋值就可以了
            event.setObject(bb.getLong(0));  // Fill with data
        }
        finally
        {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
