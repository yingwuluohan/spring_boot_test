package com.unisound.iot.controller.jdk.OutOfMemery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 栈溢出测试
 *
 * @Created by yingwuluohan on 2019/5/22.
 * @Company 北京云知声技术有限公司
 *
 * java.lang.StackOverflowError
 */
@RestController
@RequestMapping("stack/")
public class StackOverflowErrorController {

    private int num=0;

    //memory/v1
    @ResponseBody
    @RequestMapping(value = "v1" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String getStackInfo( ){

        System.out.println( "********** stack begin *********" );
        stackOverFlow( 1 );
        return "ok";
    }

    public  void stackOverFlow( int count ){
        int num =0;
        count = ++num;
        if( count < num ){
            return;
        }
        stackOverFlow( count );
    }

    public static void main(String[] args) {
        StackOverflowErrorController stack = new StackOverflowErrorController();
        stack.stackOverFlow( 1 );
    }
}
