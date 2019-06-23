package com.unisound.iot.controller.jdk.JIT;

import com.unisound.iot.common.mongo.User;

import java.util.ArrayList;
import java.util.List;

public class Child implements Osr {

    private String key;

    private Osr osr;

    public Child(String key ){
        this.key = key ;
    }





    @Override
    public String findKey() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    class ChildSmall{

        private String key ;

        public List<String > findInfo(){
            List<String> list = new ArrayList<>(10);

            list.add( "test" );
            return list;

        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }


}
