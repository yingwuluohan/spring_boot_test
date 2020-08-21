package com.unisound.iot.controller.jdk.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Created by yingwuluohan on 2020/3/22.
 */
public class FutureTaskTest {

    private Thread tr;

    public static void main(String[] args) {
        FutureTaskTest test = new FutureTaskTest();
        test.testFatureTask();
    }

    private  void testFatureTask(){
        try {
            CallableImpl callableImpl=new CallableImpl();
            FutureTask futureTask=new FutureTask<Integer>(callableImpl);

            //在主线程中开启子线程
            Thread tr = new Thread(futureTask);
            tr.start();
            System.out.println("子线程开始运行");

            //在主线程中判断子线程是否已经完成工作
//            while(!futureTask.isDone()){
//                System.out.println("在主线程中判断子线程的工作是否已经完成");
//                System.out.println("子线程的工作还在进行中...........");
//            }
            System.out.println( "线程：" + Thread.currentThread().getName() );
            //TODO 如果调用了get（）方法，则主线程会一直阻塞到子线程执行完毕有返回值为止。
            System.out.println("子线程运行结束,结果:"+futureTask.get());

        } catch (Exception e) {

        }
    }



    public class CallableImpl implements Callable<Integer> {
        private final int COUNTER = 9527;

        public CallableImpl() {

        }

        @Override
        public Integer call() throws Exception {
            try {
                System.out.println("...模拟子线程中的耗时工作...线程名称:"+ Thread.currentThread().getName());
                Thread.sleep(3000 );
                System.out.println("...模拟子线程中的耗时工作...线程名称:"+ Thread.currentThread().getName());
//                Thread.sleep(1000 * 7);
                System.out.println("...模拟子线程中的耗时工作...线程名称:"+ Thread.currentThread().getName());
                System.out.println( "线程：" + Thread.currentThread().getName() );

            } catch (Exception e) {

            }
            return COUNTER;
        }


    }



}
