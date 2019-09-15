package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import java.util.Date;

public class FileInfo {
    public static final int OOS_STORAGE_MODE = 1;
    public static final int KINGSUN_STORAGE_MODE = 2;
    public static final int OTHER_STORAGE_MODE = 3;
    private long len = 0;
    private String md5 = null;
    private String CRC = null;
    private Date lastModified = null;
    private String taskId = null;
    private String appkey = null;
    private String userid = null;
    private String format = null;
    //存储方式。1：阿里云；2：金山云；3：其他，可使用url获取文件
    private int storageMode = 1;
    private String audioUrl = null;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(int storageMode) {
        this.storageMode = storageMode;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    FileInfo() {

    }
    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
