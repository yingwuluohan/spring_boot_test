package com.unisound.iot.controller.yiWei;

/**
 * @Created by yingwuluohan on 2020/3/24.
 */
public class Move {


    public static void sort(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        System.out.println( (R - L) >> 1 );
        int mid = L + ((R - L) >> 1);

        System.out.println( mid );

    }

    public static void main(String[] args) {
        int[] array = { 2,3,4,5,6,6,10,23,25 ,33 };
        sort( array , 4, 8 );
        //TODO 右移是做除法 ，2的N次幂
        System.out.println( 8 >> 0 );
        System.out.println( 8 >> 1 );
        System.out.println( 8 >> 2 );
        //TODO 左移是做乘法 ，2的N次幂
        System.out.println( 8 << 0 );
        System.out.println( 8 << 1 );
        System.out.println( 8 << 2 );
        System.out.println( "8 >>> 0 :" + ( 8 >>> 0) );
        System.out.println( "8 >>> 1 :" + ( 8 >>> 1) );
    }















}
