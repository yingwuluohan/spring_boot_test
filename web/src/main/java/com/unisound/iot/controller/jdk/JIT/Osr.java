package com.unisound.iot.controller.jdk.JIT;

import com.unisound.iot.common.modle.dataRpc.Album;
import com.unisound.iot.common.mongo.User;

import java.util.ArrayList;
import java.util.List;

public interface Osr {


    static void findInfo(String id) {

    }

    String findKey();

    void addUser(User user );



    class innoiManage<T>{

        private String name;

        private Integer userId;


        List<T> findUserList( Integer level ){
            List<Album> list = new ArrayList<>( 100 );
            Album album = new Album();
            album.setAlbumType( "1" );
            list.add( album );
            findInfo( "" );
            return (List<T>) list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }









}
