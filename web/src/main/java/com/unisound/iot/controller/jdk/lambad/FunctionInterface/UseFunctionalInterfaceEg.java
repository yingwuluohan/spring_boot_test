package com.unisound.iot.controller.jdk.lambad.FunctionInterface;


import com.unisound.iot.controller.jdk.javap.User;

import java.util.function.Function;

/**
 * 函数式接口使用例子
 *
 *
 */
public class UseFunctionalInterfaceEg {

    public static void useInterface( FunctionInt functionInt ){

        functionInt.method();

    }


    public static void main(String[] args) {
        //使用
        useInterface( new MyFunctionInt() );

        //或者直接new 一个接口
        useInterface(new FunctionInt() {
            @Override
            public void method() {
                System.out.println( "" );
            }
        });

        //使用lambad表达式
        useInterface( ()-> {
            System.out.println( "使用labad表达式" );

        });
        //函数编程
        getVallue( func ->  String.valueOf( func.getAge()) ,new User() );

    }


    /**
     *
     * @param function ： function 的key表示传入的参数类型 为User，
     * @param           ：function 的value表示输入的结果类型 为String
     * @return
     */
    public static String getVallue(Function<User,String > function , User user ){
        String result = function.apply( user  );

        return result;
    }
















}
