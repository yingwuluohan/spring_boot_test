package com.unisound.iot.controller.jdk.concurentMapList;

import com.unisound.iot.common.mongo.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @Created by yingwuluohan on 2019/12/8.
 */
public class HashSetTest {


    public static void main(String[] args) {
        User user = new User();
        user.setPassword( "p" );
        user.setUserid("id");

        User user2 = new User();
        user2.setPassword( "p" );
        user2.setUserid("id");

        Set<User> set = new HashSet<>();
        set.add( user );
        set.add( user2 );

        System.out.println( "size:" +set.size() );
    }



}
