package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程共享资源 --各个线程资源合并
 */
public class ThreadsShareResource {




    public void mergeResourceByThread(){

        for( int i = 0 ; i < 10;i++ ){
//            Server server = new Ser
//
//            SingleServerThread t = new SingleServerThread(s, context, results, asrEndCallBack);
//            t.type = sKey;
//            t.latch = latch;
////			t.filterTime = filterTime;
//            //缓存启动的服务线程
//            singleServerThreads.add(t);
//            t.start();
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
                result.setType( "type1" );
                System.out.println( "当前线程：" + Thread.currentThread().getName() );
            }catch (Exception e ){
                e.printStackTrace();
            }finally{
                System.out.println( "ConcurrentLinkedQueue size :" + results.size() );
                results.offer(result);
            }
        }

    }
}
