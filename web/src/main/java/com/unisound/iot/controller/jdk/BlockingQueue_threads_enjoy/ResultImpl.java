package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.util.Map;

public class ResultImpl implements Result {

    private String type;
    private int statusCode;
    private String item;
    private Object object;

    private byte[] data;

    @Override
    public String getType() {
        System.out.println( type );
        return type;
    }

    @Override
    public void setType(String var1) {
        this.type = var1;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setStatusCode(int var1) {
        this.statusCode = var1;
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

    @Override
    public void setData(byte[] var1) {
        this.data = var1;
    }

    @Override
    public Map<String, Object> getItems() {
        return null;
    }

    @Override
    public Object getItem(String var1) {
        return item;
    }

    @Override
    public void setItem(String var1, Object var2) {
        this.item = var1;
        this.object = var2;

    }
}
