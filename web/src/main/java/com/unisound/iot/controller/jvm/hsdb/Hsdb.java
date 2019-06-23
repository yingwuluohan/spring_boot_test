package com.unisound.iot.controller.jvm.hsdb;

import com.unisound.iot.controller.jdk.javap.User;

public class Hsdb {


    private static User user;

    int a=5;

    public static void main(String[] args) {
        Hsdb p=new Hsdb();
        user = new User();
        user.setAge( 1 );
        p.test( user );


    }
    public void test( User user ){
        System.out.println("haha" + user.getAge() );
        user.setAge( 10000 + a  );
        try {
            Thread.sleep( 10000000L );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  ;
    }

}
