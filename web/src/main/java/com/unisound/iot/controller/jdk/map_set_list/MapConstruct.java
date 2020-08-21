package com.unisound.iot.controller.jdk.map_set_list;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2020/3/20.
 */
public class MapConstruct {


    public static void main(String[] args) {
        mapTest();
    }

    public static void mapTest(){
        Map< Integer , Integer > map = new HashMap<>();
        for( int i = 0 ; i < 100 ;i++){
            map.put( i , i );

        }

        System.out.println( map .size() );
    }



















}
