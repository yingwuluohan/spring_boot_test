package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company fn
 */
public class Message extends BaseModle< Object , Object  > implements Serializable {

    private Long id;
    /**
     *消息类型
     */
    private Integer type;
    /**
     *
     */
    private Integer status;
    /**
     * 消息内容
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(Integer type, Integer status, String content) {
        this.type = type;
        this.status = status;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", status=" + status +
                ", content='" + content + '\'' +
                '}';
    }
}
