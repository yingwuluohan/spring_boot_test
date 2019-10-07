package com.unisound.iot.controller.disruptor2;

import com.lmax.disruptor.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventProcessor {

    private static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        final RingBuffer<Task> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Task>() {
            @Override
            public Task newInstance() {
                return new Task();
            }
        }, BUFFER_SIZE , new YieldingWaitStrategy() );

        ExecutorService executors = Executors.newFixedThreadPool( 3 );

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        //创建消息处理器
        BatchEventProcessor<Task> tranprocessor = new BatchEventProcessor<>(
                ringBuffer , sequenceBarrier ,new TaskHandler() );

        //把消息者的位置信息告知给生产者，如果只有一个消费者可以省略
        ringBuffer.addGatingSequences( tranprocessor.getSequence() );
        //把消息处理器提交到线程池
        executors.submit( tranprocessor );
        //如果是多个消费者，重复执行上面三行代码，
        Future< ? > futrue = executors.submit(new Callable<Void>() {
            @Override
            public Void call(){

                return null;
            }
        });




    }


}
