package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程共享资源 --各个线程资源合并
 */
public class ThreadsShareResource {


    public static void main(String[] args) {
        ThreadsShareResource threadsShareResource = new ThreadsShareResource();
        threadsShareResource.mergeResourceByThread();
    }


    public void mergeResourceByThread(){
        //
        ConcurrentLinkedQueue<Result> results = new ConcurrentLinkedQueue<Result>();
        //
        final ConcurrentLinkedQueue<SingleServerThread> singleServerThreads =
                new ConcurrentLinkedQueue<>();
        //
        CountDownLatch latch = new CountDownLatch( 10 );
        for( int i = 0 ; i < 10;i++ ){
            Server server = new ServerImpl();
            server.setResNum( i + 10 );

            SingleServerThread t = new SingleServerThread(server , results );
            t.latch = latch;
            //缓存启动的服务线程
            singleServerThreads.add(t);
            t.start();
        }

        System.out.println( "最后合并结果集：" + results.size() );
        try {
            latch.await();
            System.out.println( "最后合并结果集：" + results.size() );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "主线程阻塞完毕--------" );
        for(int i = 0; i < results.size() ; i++) {
            Result result = results.poll();
            System.out.println( "合并后结果-type ：" + result.getType() );
        }
    }

    static class SingleServerThread extends Thread {
        Result result;
        Server server;
        ConcurrentLinkedQueue<Result> results;
        CountDownLatch latch;

        boolean isCancel = false;

        SingleServerThread(Server server,   ConcurrentLinkedQueue<Result> results ) {
            this.server = server;
            this.results = results;
        }

        @SuppressWarnings({"unchecked"})
        @Override
        public void run() {

            try{
                result = new ResultImpl();
                result.setStatusCode( 100 );
                result.setType( "type:" +latch.getCount() );
                System.out.println( "当前线程存储数据 ：" + Thread.currentThread().getName() +
                "，大小：" + latch.getCount() );
            }catch (Exception e ){
                e.printStackTrace();
            }finally{
                System.out.println( "ConcurrentLinkedQueue size :" + results.size() );
                results.offer(result);
                latch.countDown();
            }
        }

    }
}
