package com.unisound.iot.common.modle.message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//@Component
public class Container  {

    private static Container instance = new Container();

    private BlockingQueue<Message> blockingQueue = new ArrayBlockingQueue<Message>( 1000 );

    private static List<Message> list = new ArrayList<Message>( 20000 );

    private Container(   ){

    }
    public static Container getInstance(){

        return instance;
    }

    /**
     * 存放数据
     * @param message
     */
    public   void putMessage( Message message ){
        synchronized ( list ){
            if( list.size() < 20000 ){
                list.add( message );
            }else{
                try {
                    Thread.sleep( 100 );
                } catch (InterruptedException e) { }
                System.out.println( "list 大于1000 做入库操作" + message.getId() );
            }
        }
    }

    /**
     * 获取全部数据
     * @return
     */
    public   List<Message> getAllMessage(){
//        List<Message> allList = null;
//        synchronized( list ){
//            allList = list;
//            list.clear();
//        }
        return list;
    }

    public void clearList(){
        list.clear();
    }


}
