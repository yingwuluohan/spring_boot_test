package com.unisound.iot.controller.jdk.transient_serializable;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2019/6/6.
 * @Company fn
 */
public class Transient implements Serializable {

    private transient Integer code;
    private transient Integer pwd;

    public void changeInfo(){

        if( null != code ){


        }

    }


}
