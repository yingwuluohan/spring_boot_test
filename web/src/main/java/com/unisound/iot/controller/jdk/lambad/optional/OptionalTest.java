package com.unisound.iot.controller.jdk.lambad.optional;


import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalTest {

    private static List<User> list = new ArrayList<>();

    public static void main(String[] args) {
        for( int i = 0 ;i < 5 ;i++ ){
            User user = new User();
            user.setName("us" + i );
            user.setId( i );
            list.add( user );
        }
        List<User> list1 = getInfo( list );
        System.out.println( list1 );

        System.out.println( "us".contains( "us" ));
    }

    public List< User > getUserList( List< List< User > > userList){
        //<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
//        Optional.ofNullable( userList ).flatMap(   ).
        return null;

    }

    public static   List<User>  getInfo( List< User > list  ){
        List< User > users1 = Optional.ofNullable( list ).
        map( value -> {
                 List< User > users = value.stream().filter(user ->
                                 user.getName().contains( "us")


                 ).collect(Collectors.toList());
                 System.out.println(  );
                 return users;
                }
        ).orElse( new ArrayList<>( ));
        return users1;
    }

    public static Map<String, Object> parseMapForFilterByOptional(Map<String, Object> map) {
        return Optional.ofNullable(map).map(
                (v) -> {
                    Map params = v.entrySet().stream()
                            .filter((e) ->  !e.getValue().equals( "" ))
                            .collect(Collectors.toMap(
                                    (e) -> (String) e.getKey(),
                                    (e) -> e.getValue()
                            ));
                    return params;
                }
        ).orElse(null);
    }



}
