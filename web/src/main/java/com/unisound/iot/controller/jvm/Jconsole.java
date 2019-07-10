package com.unisound.iot.controller.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/6/24.
 * @Company 北京云知声技术有限公司
 */
public class Jconsole {

    byte[] bytes = new byte[ 1024 * 1024 ];

    public static void main(String[] args) throws InterruptedException {
        List<Jconsole > list = new ArrayList<>();
        System.out.println( "srart jconsole **** " );
        for( int i = 0 ;i < 100000;i++ ){
            Thread.sleep( 200);
            list.add( new Jconsole() );
        }


    }
}
