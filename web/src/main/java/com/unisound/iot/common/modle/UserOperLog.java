package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company 北京云知声技术有限公司
 */
public class UserOperLog extends BaseModle implements Serializable {

    private Long userId;

    private Integer type;

    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserOperLog{" +
                "userId=" + userId +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
