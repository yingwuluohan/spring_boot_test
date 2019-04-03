package com.unisound.iot.controller.jdk;

import com.unisound.iot.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/4/2.
 * @Company 北京云知声技术有限公司
 */

@RestController
@RequestMapping("map/")
public class JmapController {

    @Autowired
    private ReportService reportService;
    private int stackLength = 1;

    private String memory = "public String getMsgHistory(HttpServletRequest request, @PathVariable(name=\"id\") long id)";

    //map/test/12
    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/{id}",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getMsgHistory(HttpServletRequest request, @PathVariable(name="id") long id){


        try{

            while(true){
                for( int i = 0 ; i < 1000000000; i ++ ){
                    List< String > list = new ArrayList<>();
                    list.add(i +"" );
                    ThreadTest thread = new ThreadTest( list , i + "test" );
                    Thread threadGo = new Thread( thread );
                    threadGo.start();
                    System.out.println( "for循环：" + i );
                }
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }



        return "ok";
    }

    /**
     * 堆栈溢出
     * map/test/stack
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/stack",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void makeStackOverflowError(){
        stackLength++;
        makeStackOverflowError();
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/out",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void makeOutofMemoryError(){

        try{
            for( int i = 0 ;i < 10 ;i++ ){
                List<String > list = new ArrayList<>();
                ThreadTest test = new ThreadTest( list , memory );
                Thread thread = new Thread( test );
                thread.start();
            }
        }catch ( Exception e ){
            System.out.println( "抛异常："  +e.getMessage()  );
            e.printStackTrace();
        }


    }

    class ThreadTest implements Runnable{

        public ThreadTest(List<String> list, String content ){
            this.list = list;
            this.content = content;
        }

        private List<String> list;
        private String content;


        public void run(){
//          List<String > list = new ArrayList<>();
            try{
                System.out.println(  "当前线程：" + Thread.currentThread().getName() );
                while( true ){

                    list.add(  memory+"dddddd" + Math.random() );

                    System.out.println( Thread.currentThread().getName()+": " + list.size() );
                }
            }catch ( Exception e ){
                System.out.println( "抛异常：" + list.size() );
                e.printStackTrace();
            }
        }


    }



}
