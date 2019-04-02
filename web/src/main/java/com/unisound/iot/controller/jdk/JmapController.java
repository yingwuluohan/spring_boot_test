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
                    thread1 thread = new thread1( list , i + "test" );
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

    class thread1 implements Runnable{

        public thread1(List list, String content ){
            this.list = list;
            this.content = content;
        }

        private List list;
        private String content;


        public void run(){
            list.add( content );
            System.out.println( "list 大小：" + list.size() );
        }


    }



}
