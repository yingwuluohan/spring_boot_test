package com.unisound.iot.controller.jdk.weiYiSuan;

/**
 * @Created by yingwuluohan on 2020/3/20.
 */
public class Wei {


    public static void wei(){
        int a = 1;
        long begin = System.currentTimeMillis();
        for( int i = 0;i< 1000000;i++ ){
            a = a & i ;
        }
        System.out.println( "耗时：" + ( System.currentTimeMillis() - begin ));

    }


    public static void wei2(){
        int a = 1;
        long begin = System.currentTimeMillis();
        for( int i = 1;i< 1000000;i++ ){
            a = a % i ;
        }
        System.out.println( "耗时：" + ( System.currentTimeMillis() - begin ));
    }

    public static void main(String[] args) {
        wei();
    }

}
