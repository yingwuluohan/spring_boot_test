package com.unisound.iot.common.mongo;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company fn
 */
//@Document( collection="channel")
public class Channel implements Serializable {

    private String id;
    private String name;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
