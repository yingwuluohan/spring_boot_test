package com.unisound.iot.controller.jdk.stream;


import com.unisound.iot.controller.jdk.lambad.ObjectUtil;
import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsUtil {




    public static void main(String[] args) {
        Map< Integer , String > map = ObjectUtil.list.stream().collect(Collectors.toMap(User::getId , User::getName  ));
        //TODO identity 入参就是出参，因为list的参数是User
        Map< Integer , User> map2 = ObjectUtil.list.stream().collect(Collectors.toMap(User::getId , Function.identity()));

        System.out.println( map );
        System.out.println( map2 );

    }






}
