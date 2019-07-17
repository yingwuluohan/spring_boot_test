package com.unisound.iot.controller.jdk.lambad.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company 北京云知声技术有限公司
 */
public class StreamLambad {


    public static void main(String[] args) {
        List < String > list = new ArrayList( );
        list.add( "a" );
        list.add( "b" );
        list.add( "c" );
        list.add( "d" );
        list.add( "e" );
        list.add( "f" );
        list.add( "g" );

        Stream<String> stream = list.stream();


    }
















}
