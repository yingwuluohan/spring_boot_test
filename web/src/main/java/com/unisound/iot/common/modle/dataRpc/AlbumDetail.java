package com.unisound.iot.common.modle.dataRpc;

import java.io.Serializable;

/**
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2017/12/20
 **/
public class AlbumDetail implements Serializable {
    /** 付费标识（1：付费 0：免费） **/
    private Integer feeFlag;
    private Long id;
    /** 专辑名称 **/
    private String name;
    /** 专辑类型：serial|package|playlist **/
    private String albumType;
    /**首页图片地址*/
    private String logoUrl;
    /**权重*/
    private Integer level;
    /**首页展示的图片权重*/
    private Integer homeLevel =0;
    /**1：专辑合集类型，2：专辑类型，3：作品类型，*/
    private Integer dataType;
    /** 是否支持语音点播，1:是 0：否 **/
    private Integer voiceDemand;
    /** 专辑别名列表 **/
    private String[] alias;
    /** 专辑显示名称 **/
    private String displayName;
    /** 歌手（作者）如：刘德华、李白 **/
    private String author;
    private String[] authors;
    /** 艺术家，如：凯叔、单田芳 **/
    private String artist;
    private String[] artists;
    /** 专辑图片 **/
    private String imgUrl;
    /** 专辑内容数量，当"albumType"=serial时，内容数量等于总集数 **/
    private Integer totalCount;
    /** 当"albumType"=seria"时，此项必填，标示是否已完结 ，1：是，0：否 **/
    private Integer isCompleted;
    /** 专辑介绍，用于描述专辑 **/
    private String description;
    /** 专辑中作品所用的语言,目前只支持汉语，粤语和英语 （zh|cte|en） **/
    private String language;
    /** 领域名称 **/
    private String domainName;
    /** 数据套餐标识（1001,1002,2001） **/
    private Integer dataPlanCode;
    /** 第三方数据来源(1:fn 2：喜马拉雅 3：efunvidio 4:古诗文网) **/
    private Integer dataOrigin;
    /** 标签列表 **/
    private String[] tags;
    /** 是否支持句式点播，1：是，0：否 **/
    private Integer sentenceDemand;
    /** 状态 ：1：正常 2：下线 3：删除 **/
    private Integer status;
    /** 创建时间 **/
    private Long createTime;
    /** 更新时间 **/
    private Long updateTime;
    /** 专辑下的audio数组，item： 集数+id(1,5346278) **/
    private String[] audioList;

    // 标签体系，各个维度的值
    /** 时间，如：古代、现代、唐朝、宋朝等 **/
    private String dynasty;
    /** 题材（内容），如：百科、国学、宗教、探险等 **/
    /** 音乐类型标签，如：儿歌、二胡、摇滚曲等 **/
    private String genre;
    /** 场景，如：睡前、胎教、起床等 **/
    private String scene;
    /** 心情，如：安静、喜悦、优美等 **/
    private String mood;
    /** 歌手类别，如：MALE:男歌手，FEMALE:女歌手，BAND:乐队组合，ORIGINAL:网络原创 **/
    private String artistType;
    // 其它暂不支持的维度，有声读物
    /** 地区，如：中国、美国、英国等 **/
    private String area;
    /** 类型（形式），如：儿歌、故事、音乐、相声等 **/
    private String modality;
    /** 年龄段，如：幼儿、儿童、少儿等 **/
    private String ageGroup;
    /** 流行，如：最新、热门、经典等 **/
    private String popular;
    /** 来源，如：影视、文学、小说等 **/
    private String origin;

    /** 第三方数据来源的ID **/
    private String thirdApiId;

    private UrlAudioMeta urlAudioMeta;

    public Integer getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(Integer feeFlag) {
        this.feeFlag = feeFlag;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getHomeLevel() {
        return homeLevel;
    }

    public void setHomeLevel(Integer homeLevel) {
        this.homeLevel = homeLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public UrlAudioMeta getUrlAudioMeta() {
        return urlAudioMeta;
    }

    public void setUrlAudioMeta(UrlAudioMeta urlAudioMeta) {
        this.urlAudioMeta = urlAudioMeta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Integer getVoiceDemand() {
        return voiceDemand;
    }

    public void setVoiceDemand(Integer voiceDemand) {
        this.voiceDemand = voiceDemand;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getArtistType() {
        return artistType;
    }

    public void setArtistType(String artistType) {
        this.artistType = artistType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(Integer dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public String[] getAudioList() {
        return audioList;
    }

    public void setAudioList(String[] audioList) {
        this.audioList = audioList;
    }

    public String getThirdApiId() {
        return thirdApiId;
    }

    public void setThirdApiId(String thirdApiId) {
        this.thirdApiId = thirdApiId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String[] getArtists() {
        return artists;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }
}
