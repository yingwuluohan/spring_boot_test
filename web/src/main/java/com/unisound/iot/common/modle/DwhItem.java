package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company 北京云知声技术有限公司
 */
public class DwhItem extends BaseModle<ItemDate ,Object > implements Serializable {

    private Long dwhItemId;
    /**
     * 源组件主键
     */
    private Long itemId;
    /**
     * 组件类型( 1：banner :2类型，3：专栏 4：开机)
     */
    private Long itemType;
    /**
     * json 结构
     */
    private String  itemStructure;
    /**
     * 组件名称
     */
    private String  name ;
    /**
     * 权限类型 1：公开， 2：（c600 ，lite3,pro） ,3: 所有子公司 4,：具体某产品appkey
     */
    private Integer authType;
    /**
     *创建类型，1：云端的组件 2:云端的模板
     */
    private Integer createType;
    /**
     * 权限值，如果是具体某产品则对应appkey的值
     */
    private String authValue;
    /**
     * 合作商名称
     */
    private String domainName;

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getDwhItemId() {
        return dwhItemId;
    }

    public void setDwhItemId(Long dwhItemId) {
        this.dwhItemId = dwhItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getAuthValue() {
        return authValue;
    }

    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemType() {
        return itemType;
    }

    public void setItemType(Long itemType) {
        this.itemType = itemType;
    }

    public String getItemStructure() {
        return itemStructure;
    }

    public void setItemStructure(String itemStructure) {
        this.itemStructure = itemStructure;
    }
}
