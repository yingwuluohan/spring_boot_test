package com.unisound.iot.api.chart;

/**
 * Created by fn on 2017/5/19.
 */
public interface ClientServiceApi {

    public void sendMsg(String msg);

    public String receiveMsg();
}
