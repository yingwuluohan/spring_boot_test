package com.unisound.iot.controller.jdk.transient_serializable;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2019/6/6.
 * @Company 北京云知声技术有限公司
 */
public class Transient implements Serializable {

    private transient Integer code;
    private transient Integer pwd;

    public void changeInfo(){

        if( null != code ){


        }

    }


}
