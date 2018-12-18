package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
public class ItemDate extends BaseModle implements Serializable {

    private Long id;
    private Long itemId;
    /**
     * 资源主键
     */
    private Long albumId;
    /**
     * 数据类型，1：专辑，2：作品，3：专辑合集，4：网页，5：专题
     */
    private Integer dataType;
    /**
     * 1：自建组件，2：云端组件
     */
    private Integer itemCreateType;
    /**
     * 网页地址
     */
    private String url;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 资源排序值
     */
    private int sortNum;

    public Integer getItemCreateType() {
        return itemCreateType;
    }

    public void setItemCreateType(Integer itemCreateType) {
        this.itemCreateType = itemCreateType;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }



    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
