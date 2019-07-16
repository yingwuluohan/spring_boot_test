package com.unisound.iot.controller.jdk.lambad.result_is_lambad;

import java.util.Comparator;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company 北京云知声技术有限公司
 */
public class ResultLambad {

    /**
       方法的返回类型是一个函数式接口。则可以直接返回一个lambad表达式
        定义一个方法，返回类型就是一个函数式接口
        也可以直接返回匿名内部类
     */
    public static Comparator<String> find(){
//        return new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return 0;
//            }
//        };
        return ( String s1 ,String s2 )->{
            return s1.length() - s2.length() ;
        };


    }

}
