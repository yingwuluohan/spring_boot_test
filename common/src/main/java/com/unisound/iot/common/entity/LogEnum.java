package com.unisound.iot.common.entity;

/**
 * Created by Admin on 2018/10/10.
 */
public enum LogEnum {
    BUSSINESS("bussiness"),
    PLATFORM("platform"),
    DB("db"),
    EXCEPTION("exception"),     ;
    private String category;
    LogEnum(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }



}
