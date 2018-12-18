package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company 北京云知声技术有限公司
 */
public class AuthItemTemplate extends BaseModle<AuthItemTemplate , Object> implements Serializable {

    private Long id;
    private Long itemId;
    private Long templateId;
    /**
     *创建类型，1：云端的组件 2:云端的模板
     */
    private Integer createType;
    /**
     *审核状态 ： 0未审核 ，1 审核通过 ，2：审核未通过
     */
    private Integer status;
    /**
     *权限类型 1：公开， 2：（c600 ，lite3,pro） ,3: 所有子公司 4,：具体某产品appkey
     */
    private Integer authType;
    /**
     *权限值，如果是具体某产品则对应appkey的值
     */
    private String authValue;
    /**
     * 合作商
     */
    private String domainName;
    /**
     * 审核人名称
     */
    private String checker;

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

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

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
