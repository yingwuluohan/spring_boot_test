package com.unisound.iot.common.modle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company 北京云知声技术有限公司
 */
public class templateItem implements Serializable {

    private Long id;
    private Long itemId;
    /**
     * 模板主键
     */
    private Long templateId;
    /**
     * 组件来源( 1:自建的，2:云端的)
     */
    private Integer type;
    /**
     * 排序值
     */
    private Integer sortNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}
