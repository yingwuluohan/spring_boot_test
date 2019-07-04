package com.unisound.iot.controller.jdk.javap;

public class TestUser {
    private int count;
    private static volatile int num;
    public void test(int a){
        count = count + a;
    }

    public User initUser(int age,String name){
        User user = new User();
        user.setAge(age);
        user.setName(name);
        return user;
    }

    public void changeUser(User user,String newName){
        user.setName(newName);
    }
}
