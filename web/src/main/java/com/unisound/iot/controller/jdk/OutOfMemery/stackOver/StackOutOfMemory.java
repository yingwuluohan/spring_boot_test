package com.unisound.iot.controller.jdk.OutOfMemery.stackOver;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 每个线程分配到的栈容量越大，可以建立的线程数量自然越少，建立线程越容易耗尽剩下内存
 * VM Args：-Xss2M （这时候不妨设大些）
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
 */
@RestController
@RequestMapping("stack/threads/")
public class StackOutOfMemory {

    private void dontStop() {
        while (true) {
        }
    }
    @ResponseBody
    @RequestMapping(value = "v1" ,method = {RequestMethod.GET, RequestMethod.POST})
    public void stackLeakByThread() {
        System.out.println( "current thread :" + Thread.currentThread().getName() );
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) throws Throwable {
        StackOutOfMemory stack = new StackOutOfMemory();
        stack.stackLeakByThread();
    }



}
