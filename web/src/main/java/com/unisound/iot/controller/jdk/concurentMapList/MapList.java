package com.unisound.iot.controller.jdk.concurentMapList;

import com.unisound.iot.common.mongo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Created by yingwuluohan on 2019/12/4.
 */
public class MapList {


    public static  void testMap(){
        //避免扩容时产生循环链表 ，给与初始化
        HashMap<Integer , String > map = new HashMap<>( 32 ,1);
        ConcurrentHashMap cum = new ConcurrentHashMap( 16 ,32 ,1 );
        map.put( 1, "1");
        map.put( 2, "2");
        map.put( 3, "3");
        for( int i = 0;i < 1000;i++ ){
            map.put( i , i+"" );
        }

        System.out.println( map.size() );
    }

    public static void main(String[] args) {
        Map<User , String > map = new HashMap<>();
//        for ( int i = 0 ;i < 3 ;i++ ){
//            User user =new User();
//            user.set_id( "id");
//            user.setPassword( "pwd " );
//            map.put( user , i+"" );
//            if( i == 2 ){
//                String value = map.get( user );
//                System.out.println( value );
//            }
//        }
//
//        System.out.println( map.size() );
        testMap();

    }
}
