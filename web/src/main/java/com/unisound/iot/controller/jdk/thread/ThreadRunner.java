package com.unisound.iot.controller.jdk.thread;

import com.unisound.iot.copy_asr.api.ActionInterface;

import java.util.concurrent.*;

public class ThreadRunner  {


    private ActionInterface action ;

    private boolean isWait;



    public ThreadRunner(ActionInterface action , Object lock){
        this.action = action;

    }
    private static ExecutorService executor = new ThreadPoolExecutor( 4, 4,
            10L, TimeUnit.SECONDS , new LinkedBlockingQueue<Runnable>()  );

    public ActionInterface getAction() {
        return action;
    }

    public void setAction(ActionInterface action) {
        this.action = action;
    }

    public static void runTask(Object lock  ){
        executor.execute( new ReleaseLock( lock ) );
    }


    static class ReleaseLock implements Runnable{

        private Object lock;

        public ReleaseLock(Object lock ){
            this.lock = lock;
        }

        @Override
        public void run( ) {


            System.out.println( Thread.currentThread().getName() + " begin waiting!");
            long waitTime = System.currentTimeMillis();
            try {
                synchronized( lock ){
                    lock.wait( 10000 );
                }
                System.out.println( "lock 线程 休眠5秒开始 ***************** " );
                Thread.sleep( 5000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( "内部类获得锁 ***************** " );
            waitTime = System.currentTimeMillis() - waitTime;
            System.out.println("wait time :"+waitTime);
            System.out.println( Thread.currentThread().getName()  + " end waiting!");

        }
    }



}
