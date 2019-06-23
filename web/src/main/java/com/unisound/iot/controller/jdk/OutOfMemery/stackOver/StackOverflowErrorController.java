package com.unisound.iot.controller.jdk.OutOfMemery.stackOver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError
 * 如果虚拟机在扩展栈时无法申请到足够的内存空间，将抛出OutOfMemoryError
 * VM Args：-Xss128k
 * stack length:
 Exception in thread "main" java.lang.StackOverflowError
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
