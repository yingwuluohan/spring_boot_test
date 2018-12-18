package com.unisound.iot.common.rest;

import com.unisound.iot.common.entity.ResponseStatusEnum;

import java.io.Serializable;

/**
 * Created by Admin on 2018/9/30.
 */
public class ResponseStream <T> implements Serializable {


    //版本号
    private String version;


    //状态码
    private String code;

    //响应结果描述
    private String message;

    //响应结果集
    private T data;

    public ResponseStream(String code , String message , T data){
        this.version = "1.0";
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
