package com.unisound.iot.common.modle.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yingwuluohan on 2018/10/6.
 */
public class BaseModle< T ,A > implements Serializable {

    private String  scopeType;
    /**
     * 维度值
     */
    private String  appkey;

    private String creator;
    private String updater;
    /**
     * 创建日期
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateime;


    /**
     * 创建人主键
     */
    private Long creatorId;
    /**
     * 更新人主键
     */
    private Long updaterId;
    /**
     * 合作商主键
     */
    private Long domainId;
    /**
     * 产品类型 （1：C600 ，2：lite3, 3:pro，4 other）
     */
    private Integer  productType;
    /**
     *
     */
    private Integer  dataType;
    /**
     * 网页地址
     */
    private String  webUrl;

    private List<T> list;

    private List<A> longList;

    public List<A> getLongList() {
        return longList;
    }

    public void setLongList(List<A> longList) {
        this.longList = longList;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }



    public String getUpdateime() {
        return updateime;
    }

    public void setUpdateime(String updateime) {
        this.updateime = updateime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }
}
