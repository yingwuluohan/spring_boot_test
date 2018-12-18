package com.unisound.iot.common.modle.dataRpc;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * 专辑合集分类对象
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2018/1/9
 **/
public class AlbumType extends BaseModle implements Serializable{

    private static final long serialVersionUID = 8876279664313599555L;

    private Long albumTypeId;
    /**
     * 专辑主键
     */
    private Long albumId;
    /**首页展示的图片权重*/
    private Integer homeLevel =0;
    private String albumTypeName;
    /**权重*/
    private Integer level;
    /**1：专辑合集类型，2：专辑类型，3：作品类型，*/
    private Integer dataType;

    /** 付费标识（1：付费 0：免费） **/
    private Integer feeFlag;

    public Integer getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(Integer feeFlag) {
        this.feeFlag = feeFlag;
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

    @Override
    public Integer getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getAlbumTypeId() {
        return albumTypeId;
    }

    public void setAlbumTypeId(Long albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getAlbumTypeName() {
        return albumTypeName;
    }

    public void setAlbumTypeName(String albumTypeName) {
        this.albumTypeName = albumTypeName;
    }
}
