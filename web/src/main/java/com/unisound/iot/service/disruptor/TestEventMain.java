package com.unisound.iot.service.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.unisound.iot.service.disruptor.entity.ObjectEvent;
import com.unisound.iot.service.disruptor.factory.ObjectEventFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class TestEventMain {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        // 执行器，用于构造消费者线程
        Executor executor1 = new ThreadPoolExecutor( 8 ,10 ,0L ,
                TimeUnit.SECONDS ,new LinkedBlockingQueue<Runnable>());
        ExecutorService executor = Executors.newFixedThreadPool(4 );
                // 指定事件工厂
        ObjectEventFactory factory = new ObjectEventFactory();

        // 指定 ring buffer字节大小, must be power of 2.
        int bufferSize = 64;

        //单线程模式，获取额外的性能
        Disruptor<ObjectEvent> disruptor = new Disruptor<ObjectEvent>(
                factory,
                32,
                executor,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new MessageHandler() );
        //启动disruptor线程
        disruptor.start();

        // 获取 ring buffer环，用于接取生产者生产的事件
        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
        //为 ring buffer指定事件生产者
        //LongEventProducer producer = new LongEventProducer(ringBuffer);
        ObjectEventProducerWithTranslator producer=new ObjectEventProducerWithTranslator(ringBuffer);
        ByteBuffer buffer = ByteBuffer.allocate(8);//预置8字节长整型字节缓存
        for (long l = 0; l < 10000 ; l++)
        {
            buffer.putLong(0, l);
            producer.product( buffer );//生产者生产数据
//            Thread.sleep(300 );
        }

    }
}