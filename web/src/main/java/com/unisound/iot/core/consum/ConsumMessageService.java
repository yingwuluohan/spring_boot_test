package com.unisound.iot.core.consum;

import com.unisound.iot.common.modle.message.Container;
import com.unisound.iot.common.modle.message.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Service
public class ConsumMessageService     {

    private volatile int size;
//    @Autowired
//    private Container container;


    public void consumMessage(){
        start();
    }

    ExecutorService executor =  new ThreadPoolExecutor(4, 8,
            6, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>( 100 )) ;




    private void start(){
        while( true ){
            executor.execute( new Task() );
            try {
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class Task implements Runnable{

        public Task(){}

        public void run(){
            List< Message > list = Container.getInstance().getAllMessage();
            if( list.size() > 0 ){

                System.out.println( "消费线程获取容器" + list.size());
                System.out.println( "消费线程入库操作" );
                try {
                    Thread.sleep( 700 );
                    Container.getInstance().clearList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println( "*********************容器暂无数据*****************" );
            }

        }



    }













}
