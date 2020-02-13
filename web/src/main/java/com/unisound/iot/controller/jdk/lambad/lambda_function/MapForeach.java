package com.unisound.iot.controller.jdk.lambad.lambda_function;


import com.unisound.iot.common.modle.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapForeach {



    public List getMap(){
        Map<User, BaseResponse> dataMap = new HashMap<>( );
        List< User > userList = new ArrayList<>( dataMap.size() );
        dataMap.forEach( ( ( key , value ) -> {
            userList.add(  new User( key.getId() , value.getMessage() ));

        }));



        return userList;
    }

    public void getInsetence(){
        Map map = new HashMap<String, Integer>() {
            {
                put("assignId", 2);
                put("assignType", 1);
            }
        };

    }




}
