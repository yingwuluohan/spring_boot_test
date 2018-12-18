package com.unisound.iot.common.modle.dataRpc;

import java.io.Serializable;

/**
 * 专辑分类子对象
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2018/1/9
 **/
public class AlbumTypeDetail implements Serializable {

    private Integer albumTypeDetailId;
    /**
     * 主表主键
     */
    private Long albumTypeId;
    /**
     * 专辑主键
     */
    private Long albumId;
    /**
     * 权重
     */
    private Integer albumLevel;

    private String creator;


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getAlbumTypeDetailId() {
        return albumTypeDetailId;
    }

    public void setAlbumTypeDetailId(Integer albumTypeDetailId) {
        this.albumTypeDetailId = albumTypeDetailId;
    }

    public Long getAlbumTypeId() {
        return albumTypeId;
    }

    public void setAlbumTypeId(Long albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Integer getAlbumLevel() {
        return albumLevel;
    }

    public void setAlbumLevel(Integer albumLevel) {
        this.albumLevel = albumLevel;
    }
}
