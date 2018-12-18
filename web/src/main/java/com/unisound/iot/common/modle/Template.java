package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * Created by yingwuluohan on 2018/10/6.
 */
public class Template extends BaseModle implements Serializable {

    private Long id;
    /**
     * 父级模板主键
     */
    private Long sourceTemplateId;
    /**
     * 模板名称
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceTemplateId() {
        return sourceTemplateId;
    }

    public void setSourceTemplateId(Long sourceTemplateId) {
        this.sourceTemplateId = sourceTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
