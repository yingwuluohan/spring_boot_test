package com.unisound.iot.controller.jdk.lambad;


import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LambdaLianxi {


    List<User> list = Arrays.asList(
            new User( 1 ,"a" ),
            new User( 2 ,"b" ),
            new User( 3 ,"c" ),
            new User( 4 ,"d" )
    ) ;

    public void testCompare(){
        Collections.sort( list , ( e , e2 ) -> {
            if( e.getId() == e2.getId() ){
                return e.getName().compareTo( e2.getName() );
            }else{
                return e.getName().compareTo( e2.getName() );
            }
        });

    }




    interface FanInterface< T , R>{

         R getFan(T t, T t2);
    }

    public void getNum( Integer integer ,Integer integera ,FanInterface fan ){
        System.out.println( fan.getFan( integer , integera ));
//        Integer value = fan.getFan( integer , integera );

//        return value ;
    }

    public Integer getLambdaFan(){

//         getNum( 12, 23 ,( x , y ) ->  x + y );
        return 1;
    }







}
