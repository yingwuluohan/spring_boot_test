package com.unisound.iot.common.modle.dataRpc;
import java.io.Serializable;

/**
 * 查询专辑参数对象
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2018/10/10
 **/
public class Album implements Serializable {
    private static final long serialVersionUID = 6340497872908525167L;
    /**
     * 专辑主键
     */
    private Long id;
    /**
     * 专辑名称
     */
    private String name;
    /**
     * 专辑类型：serial|package|playlist
     */
    private String albumType;
    /**
     * 内容来源 /内容提供商
     */
    private Integer dataOrigin;
    private Integer pageNo;
    private Integer pageCount=10;
    /** 1: 正常状态*/
    private Integer status=1;
    /**标签*/
    private String tags;
    /**专辑展示名称*/
    private String displayName;
    /**领域（music、audio、poem）*/
    private String domainName;
    /**别名*/
    private String alias;
    /**语言类型粤语和英语 （zh|cte|en*/
    private String language;
    /**是否支持免句式点播(1：是，0：否 )*/
    private Integer sentenceDemand;
    /**数据套餐*/
    private Integer dataPlanCode;
    /**是否参与语音点播，1:是 0：否*/
    private Integer voiceDemand;
    private String startTime;
    /**
     * 结束时间，指的是创建时间
     */
    private String endTime;
    /** 专辑所属appKey*/
    private String appKey;
    /** 类别*/
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPageNo() {
        if( null == pageNo )
            return 1;
        return pageNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getSentenceDemand() {
        return sentenceDemand;
    }

    public void setSentenceDemand(Integer sentenceDemand) {
        this.sentenceDemand = sentenceDemand;
    }

    public Integer getDataPlanCode() {
        return dataPlanCode;
    }

    public void setDataPlanCode(Integer dataPlanCode) {
        this.dataPlanCode = dataPlanCode;
    }

    public Integer getVoiceDemand() {
        return voiceDemand;
    }

    public void setVoiceDemand(Integer voiceDemand) {
        this.voiceDemand = voiceDemand;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public Integer getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(Integer dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
