package com.unisound.iot.common.modle;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    private Integer id;

    private String code;

    private String name;

    public static List< Order > builds (){
        List< Order > list = new ArrayList<>( 10 );
        list.add( new Order() );
        return list;
    }



}
