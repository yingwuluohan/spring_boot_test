package com.unisound.iot.controller.dubbo.register;

import com.unisound.iot.controller.dubbo.frame.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2019/8/2.
 */
public class RemoteMapRegister {


    private static Map<String , List<Url>> register = new HashMap<>();

    public static void setRegister( String interfaceName , Url url ){
        List< Url > list = register.get( interfaceName );
        if( list == null ){

            list = new ArrayList<>();
        }
        list.add( url );
        register.put( interfaceName , list );
    }

    public static List<Url > get( String interfaceName ){
        return register.get( interfaceName );
    }












}
