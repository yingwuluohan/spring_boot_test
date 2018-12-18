package com.unisound.iot.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
public class ItemVo implements Serializable {

    private Long itemId;
    /**
     * 当前操作组件的合作商id
     */
    private Long operDomainId;
    /**
     * 组件所属的合作商id
     */
    private Long domainId;
    /**
     * 上层公司主键
     */
    private Long parendDomainId;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 当前公司维度 ：yzs ,appkey
     */
    private String scopeType;
    private String updater;
    /**
     * 操作人主键
     */
    private Long updaterId;

    /**
     * 组件类型( 1：banner :2类型，3：专栏 4：开机)
     */
    private Integer itemType;
    /**
     * 权限类型 1：公开， 2：（c600 ，lite3,pro） ,3: 所有子公司 4,：具体某产品appkey
     */
    private Integer authType;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 1：自建 ，2：云端
     */
    private Integer itemCreateType;
    /**
     * 公司的产品类型
     */
    private List< String > productTypeList;
    /**
     * 当前公司的所有appkey
     */
    private List< String > appkeyList;


    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Long getOperDomainId() {
        return operDomainId;
    }

    public void setOperDomainId(Long operDomainId) {
        this.operDomainId = operDomainId;
    }

    public Integer getItemCreateType() {
        return itemCreateType;
    }

    public void setItemCreateType(Integer itemCreateType) {
        this.itemCreateType = itemCreateType;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public List<String> getAppkeyList() {
        return appkeyList;
    }

    public void setAppkeyList(List<String> appkeyList) {
        this.appkeyList = appkeyList;
    }

    public List<String> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<String> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public Long getParendDomainId() {
        return parendDomainId;
    }

    public void setParendDomainId(Long parendDomainId) {
        this.parendDomainId = parendDomainId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }
}
