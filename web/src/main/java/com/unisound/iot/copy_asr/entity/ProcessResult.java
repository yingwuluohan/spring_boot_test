package com.unisound.iot.copy_asr.entity;

import com.unisound.iot.copy_asr.api.Result;

import java.util.HashMap;
import java.util.Map;

public class ProcessResult implements Result {
    private static final long serialVersionUID = 1L;
    private String type;
    private byte[] data;
    private int statusCode = 200;
    private Map<String, Object> items = new HashMap();

    public ProcessResult() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Map<String, Object> getItems() {
        return this.items;
    }

    public Object getItem(String item) {
        return this.items.get(item);
    }

    public void setItem(String key, Object value) {
        this.items.put(key, value);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

}
