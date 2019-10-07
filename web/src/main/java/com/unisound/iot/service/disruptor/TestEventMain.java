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
    static ExecutorService executor = Executors.newFixedThreadPool(4 );
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        // 执行器，用于构造消费者线程
        Executor executor1 = new ThreadPoolExecutor( 6 ,6 ,0L ,
                TimeUnit.SECONDS ,new LinkedBlockingQueue<Runnable>());
        //一定是2的指数倍，缓冲区大小
        int ringBufferSize = 1024 * 1024;
                // 指定事件工厂
        ObjectEventFactory factory = new ObjectEventFactory();

        // 指定 ring buffer字节大小, must be power of 2.
        int bufferSize = 64;

        //单线程模式，获取额外的性能
        //TODO ProducerType.SINGLE ：生产者只有一个
        //TODO ProducerType.MULTI ：生产者只有多个
        //TODO 消费生产策略
        //TODO BlockingWaitStrategy ：
        //TODO SleepingWaitStrategy ： 对生产者线程影响小
        //TODO YieldingWaitStrategy ： 适合低延迟系统，


        Disruptor<ObjectEvent> disruptor = new Disruptor<ObjectEvent>(
                factory,
                ringBufferSize,
                executor,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new MessageHandler() );
        //启动disruptor线程
        disruptor.start();

        // 获取 ring buffer环，用于接取生产者生产的事件
        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
        //为 ring buffer指定事件生产者,生产者生产的数据都放到ringbuffer里面
        ObjectEventProducer producer = new ObjectEventProducer(ringBuffer);
//        ObjectEventProducerWithTranslator producer=new ObjectEventProducerWithTranslator(ringBuffer);
        ByteBuffer buffer = ByteBuffer.allocate(8);//预置8字节长整型字节缓存
        for (long l = 0; l < 10000 ; l++)
        {
            buffer.putLong(0, l);
            producer.product( buffer );//生产者生产数据
//            Thread.sleep(300 );
//            ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executor);
        }


        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executor);
        int queueSize = tpe.getQueue().size();
        System.out.println("当前排队线程数："+ queueSize);
        int activeCount = tpe.getActiveCount();
        System.out.println("当前活动线程数："+ activeCount);

        long completedTaskCount = tpe.getCompletedTaskCount();
        System.out.println("执行完成线程数："+ completedTaskCount);

        long taskCount = tpe.getTaskCount();
        System.out.println("总线程数："+ taskCount);

    }


    static class putData implements Runnable{

        private ObjectEventProducerWithTranslator producer;
        private ByteBuffer buffer;
        public putData(ObjectEventProducerWithTranslator producer,ByteBuffer buffer ){
            this.producer = producer;
            this.buffer = buffer;

        }

        @Override
        public void run() {
            producer.product( buffer );
        }





    }
}
