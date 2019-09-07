package com.unisound.iot.controller.jdk.happen_befor;

/**
 * @Created by yingwuluohan on 2019/9/6.
 */
public class HappentBefor {


    public static int a = 0;
    private static boolean flag=false;

    public static void main(String[] args) {

        Thread t = new Thread( ()->{
            a = 1;
            flag = true;
        });
        Thread tt = new Thread( ()->{
            if( flag ){
                a*=1;
            }

            if( a ==0 ){
                System.out.println( "tt a = "+ a );
            }
        });

        t.start();
        tt.start();
        try {
            t.join();
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }










}
