package com.unisound.iot.service.disruptor.entity;

import java.io.Serializable;



public class ObjectEvent implements Serializable{


    private Object object;
    public Object getObject() {
        return object;
    }
    public ObjectEvent setObject(Object object) {
        this.object = object;
        return this;
    }
}
