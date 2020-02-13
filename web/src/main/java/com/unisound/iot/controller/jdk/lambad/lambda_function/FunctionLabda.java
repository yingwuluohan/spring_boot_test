package com.unisound.iot.controller.jdk.lambad.lambda_function;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FunctionLabda {

    private static List< Integer > list = Arrays.asList( 1,2,3,4,5,56,57);
    private static List<User> userList = Arrays.asList(
            new User( 1 ,"b" ),
            new User( 3 ,"c" ),
            new User( 4 ,"d" ),
            new User( 5 ,"dd" )
    ) ;
    public static void main(String[] args) {
        Map< String , Integer > map = transHashMapFromList( userList , User::getName , User::getNum );

        System.out.println( map );
    }

    public static <T,U,R> Map<U,T> transHashMapFromList(List<R> list, Function<R,U> keyFunction,
                                                        Function<R,T> valueFunction){
        if(list == null){
            return Maps.newHashMap();
        }
        return list.stream()
                .collect(HashMap::new, (m, v)->m.put(keyFunction.apply(v), valueFunction.apply(v)), HashMap::putAll);
    }


}
