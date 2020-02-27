package com.unisound.iot.common.vo;/**
 * Created by Admin on 2018/1/9.
 */

import java.io.Serializable;

/**
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2018/1/9
 **/
public abstract class BaseVO implements Serializable{
    /**
     * 公司主键
     */
    private Integer companyId;
    private String scopeValue;
    /**
     * 所属类型（fn：yzs ，一级公司：clientName ，APP：app）
     */
    private String scopeType;
    /**
     * logo图片地址
     */
    private String logoUrl  ;
    /**
     * 小图地址
     */
    private String smallFileLogoUrl  ;
    private String createTime  ;
    private String creator  ;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSmallFileLogoUrl() {
        return smallFileLogoUrl;
    }

    public void setSmallFileLogoUrl(String smallFileLogoUrl) {
        this.smallFileLogoUrl = smallFileLogoUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
