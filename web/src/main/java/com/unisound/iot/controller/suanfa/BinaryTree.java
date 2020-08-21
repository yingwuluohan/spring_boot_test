package com.unisound.iot.controller.suanfa;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Created by yingwuluohan on 2020/3/27.
 */
public class BinaryTree {


    private static Queue  queue = new ArrayBlockingQueue( 10 );



    public static void setNode(){

        for( int i =0 ; i < 1;i++ ){

            queue.add( i );
        }
    }

    public static void getNode(){
        for( int i =0 ; i < 11;i++ ){

            int n = ( int )queue.poll();
            System.out.println( "node:" + n );
        }
    }




    public static void main(String[] args) {
        setNode();
        getNode();
    }

}
