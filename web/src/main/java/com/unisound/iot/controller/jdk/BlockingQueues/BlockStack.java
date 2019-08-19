package com.unisound.iot.controller.jdk.BlockingQueues;

import java.util.Stack;

/**
 * @Created by yingwuluohan on 2019/4/11.
 *
 *
 */
public class BlockStack {

    private static Stack<Object > stack = new Stack<>();

    public static synchronized void test(){
        stack.add( "a" );
        stack.push( "b" );
        stack.push( "c" );
    }


    public static void main(String[] args) {
        test();
        System.out.println( "stack:" +stack.peek() );
        System.out.println("stack:" +stack.pop());

    }

}
