package com.unisound.iot.controller.jdk.threads_notify;

import lombok.Data;

@Data
public class Resource {


    private String name;
    private Integer age;

    private boolean flag;

    public synchronized void set(String name , Integer age ){
        this.name= name;
        this.age = age;
    }

    public synchronized void out(){
        System.out.println( age + " shuchu " + name );
    }

}
