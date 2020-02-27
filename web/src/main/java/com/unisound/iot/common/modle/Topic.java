package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * 专题对象
 * @Created by yingwuluohan on 2018/10/6.
 * @Company fn
 */
public class Topic extends BaseModle< TopicData ,Object > implements Serializable{
    private Long id;
    /**
     * 分类主键
     */
    private Long categoryd;
    /**
     * 专题名称
     */
    private String name;
    /**
     * 分类名称
     */
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryd() {
        return categoryd;
    }

    public void setCategoryd(Long categoryd) {
        this.categoryd = categoryd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "categoryd=" + categoryd +
                ", name='" + name + '\'' +
                '}';
    }
}
