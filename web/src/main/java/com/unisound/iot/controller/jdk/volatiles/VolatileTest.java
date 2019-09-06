package com.unisound.iot.controller.jdk.volatiles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Created by yingwuluohan on 2019/8/5.
 */
@RestController
@RequestMapping("queue/")
public class  VolatileTest {

    private volatile int num;


    private static ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue( 100 );
//    static byte[] content = new byte[1024 * 1024 ];

    @ResponseBody
    @RequestMapping(value = "test" ,method = {RequestMethod.GET, RequestMethod.POST})
    public static void testMethod( ) throws InterruptedException {


        for( int i =0 ;i < 100   ;i++ ){
            byte[] content = new byte[1024 * 1024*10 ];
            blockingQueue.put( content );


             System.out.println( "队列大小是:" + blockingQueue.size() );

        }



    }


    public void put(String value ){
        try {

            blockingQueue.put( value );
            num++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public Object take(){
        Object value = null;
        try {
            value = blockingQueue.take();
            num--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

}
