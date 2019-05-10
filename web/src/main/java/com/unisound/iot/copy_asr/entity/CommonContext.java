package com.unisound.iot.copy_asr.entity;


import com.unisound.iot.copy_asr.api.Context;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CommonContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> attributes = new ConcurrentHashMap();
    private String id;
    private Object input;
    private Object output;

    public CommonContext(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        if (value != null) {
            this.attributes.put(key, value);
        }
    }

    public Set<String> attributeKeys() {
        return this.attributes.keySet();
    }

    public Object getInput() {
        return this.input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public Object getOuput() {
        return this.output;
    }

    public void setOuput(Object output) {
        this.output = output;
    }

    public String toString() {
        return "CommonContext [attributes=" + this.attributes + ", id=" + this.id + ", input=" + this.input + ", output=" + this.output + "]";
    }

    public void clear() {
        this.attributes.clear();
        this.attributes = null;
    }
}
