package com.unisound.iot.controller.jdk.thread;

import com.unisound.iot.copy_asr.api.ActionInterface;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadRunner  {


    private ActionInterface action ;

    private boolean isWait;
    /** sun.misc */

    private static Unsafe getUnsafeInstance() throws SecurityException,  NoSuchFieldException, IllegalArgumentException,  IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }


    public ThreadRunner(ActionInterface action , Object lock){
        this.action = action;

    }
    private static ExecutorService executor = new ThreadPoolExecutor( 0, 5,
            1L, TimeUnit.SECONDS , new LinkedBlockingQueue<Runnable>( 5 )  ,
            Executors.defaultThreadFactory() ,new ThreadPoolExecutor.DiscardPolicy());

    public ActionInterface getAction() {
        return action;
    }

    public void setAction(ActionInterface action) {
        this.action = action;
    }

    public static void runTask(Object lock  , AtomicInteger threadNum){
        executor.execute( new ReleaseLock( lock ,threadNum ) );
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        AtomicInteger threadNum = new AtomicInteger( 10 );
        Unsafe unsafe = getUnsafeInstance();
        System.out.println( "size:" + unsafe.allocateMemory( 1024 ));
        for( int i = 0 ; i< 10 ;i++ ){
            runTask( new Object() ,threadNum );
        }
    }


    static class ReleaseLock implements Runnable{

        private Object lock;
        private AtomicInteger count = new AtomicInteger();

        public ReleaseLock(Object lock  ,AtomicInteger count ){
            this.lock = lock;
            this.count = count;
        }

        @Override
        public void run( ) {

            count.decrementAndGet();
            System.out.println( Thread.currentThread().getName()+ ":"+count.get() + "次 begin waiting!");
            long waitTime = System.currentTimeMillis();
            try {
                synchronized( lock ){
                    lock.wait( 1000 );
                }
//                System.out.println( "lock 线程 休眠5秒开始 ***************** " );
                Thread.sleep( 2000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println( "内部类获得锁 ***************** " );
            waitTime = System.currentTimeMillis() - waitTime;
//            System.out.println("wait time :"+waitTime);
            System.out.println( Thread.currentThread().getName()  + " end waiting!");

        }
    }



}
