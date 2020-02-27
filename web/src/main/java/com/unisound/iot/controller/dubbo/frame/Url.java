package com.unisound.iot.controller.dubbo.frame;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company fn
 */
public class Url implements Serializable{
    private String host;
    private Integer port;


    public Url(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
