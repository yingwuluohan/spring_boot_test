package com.unisound.iot.controller.jdk.lambad.lambda_function;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class User {

    private int id;
    private String name;
    private Integer num;
    private List< Integer > list;
    private static User instance;

    private Modle modle;

    public static User getInstance() {
        if( null == instance ){
            instance = new User( 111, "test" );
        }
        return instance;
    }

    public Modle getModle() {
        return modle;
    }

    public void setModle(Modle modle) {
        this.modle = modle;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public User(){}
    public User(int id ,int num ){
        this.num = num;
        this.id = id ;
    }
    public User(int id  ){
        this.id = id ;
    }

}
