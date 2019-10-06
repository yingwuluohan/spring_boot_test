package com.unisound.iot.controller.design_modle.guanChaZhe;

/**
 * Created by yingwuluohan on 2017/9/4.
 */
public class Test {

    private static String only = "only";

    public static String hebing(String str ){
        str = str + "test";
        return str;
    }


    public static void main(String[] args){

        String s = new String( "string" );
        hebing( s );
        System.out.println( s );
        System.out.println( hebing( only ) );
        SimpleObservable doc = new SimpleObservable ();
        SimpleObserver view = new SimpleObserver (doc);
        doc.setData(1);
        doc.setData(2);
        doc.setData(2);
        doc.setData(3);
    }
}
