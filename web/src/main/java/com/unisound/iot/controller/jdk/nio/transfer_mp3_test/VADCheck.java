package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VADCheck {
    private long handle = 0;
    private static final Logger logger = LoggerFactory.getLogger(VADCheck.class);
    /**
     * 启动
     */
    public VADCheck(){
        handle = UniVadnn.uniVadInit(UniVadnn.getVadnnDatPath());
        UniVadnn.uniVadSetOption(handle, 0, "500");
    }

    /**
     * 静音检查
     * @param buff
     * @param len
     * @return
     */
    public boolean check(byte[] buff, int len) {
        int ret = UniVadnn.uniVadProcess(handle, buff, len);
        return ret>0;
    }

    /**
     * 释放资源
     * @return
     */
    public void release(){
        UniVadnn.uniVadReset(handle);
        UniVadnn.uniVadFree(handle);
    }
}
