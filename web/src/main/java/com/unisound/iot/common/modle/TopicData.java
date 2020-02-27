package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;
import com.unisound.iot.common.modle.dataRpc.AlbumDetail;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company fn
 */
public class TopicData extends BaseModle implements Serializable {

    private Long id;
    /**
     * 专题主键
     */
    private Long topicId;
    /**
     * 资源主键
     */
    private Long albumId;
    /**
     * 排序
     */
    private Long sortum;
    /**
     * 资源类型
     */
    private Integer dataType;
    /**专辑 或者 作品对象*/
    private AlbumDetail albumDetail;

    public AlbumDetail getAlbumDetail() {
        return albumDetail;
    }

    public void setAlbumDetail(AlbumDetail albumDetail) {
        this.albumDetail = albumDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getSortum() {
        return sortum;
    }

    public void setSortum(Long sortum) {
        this.sortum = sortum;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
