package com.unisound.iot.common.modle;

import com.unisound.iot.common.modle.base.BaseModle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yingwuluohan on 2018/10/6.
 */
public class Item extends BaseModle< ItemDate , Long > implements Serializable {

    private Long itemId;
    /**
     * 父级主键
     */
    private Long sourceItemId;

    /**
     * 父级组件主键
     */
    private Long sourcetemd;
    /**
     * 组件名称
     */
    private String name;

    /**
     * 组件类型( 1：banner :2类型，3：专栏 4：开机)
     */
    private Integer itemType;
    /**
     * 1：自建 ，2：云端
     */
    private Integer itemCreateType;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 组件json结构
     */
    private String  itemStructure;
    /**
     * 所有组件引用的资源id，用逗号隔开 （ 同平台第一个版本的逻辑 ）
     */
    private String  albumIdStr;
    /**
     * 资源变更的状态（ 0：新增，1：删除 ， 2：不变 ）
     */
    private String  albumStatus;
    /**
     * 组件资源关系主键
     */
    private List< Long > itemDataIdList;

    public List<Long> getItemDataIdList() {
        return itemDataIdList;
    }

    public void setItemDataIdList(List<Long> itemDataIdList) {
        this.itemDataIdList = itemDataIdList;
    }

    public Long getSourceItemId() {
        if( null == sourceItemId ){
            sourceItemId = 0L;
        }
        return sourceItemId;
    }

    public Integer getItemCreateType() {
        return itemCreateType;
    }

    public void setItemCreateType(Integer itemCreateType) {
        this.itemCreateType = itemCreateType;
    }

    public void setSourceItemId(Long sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public String getAlbumIdStr() {
        return albumIdStr;
    }

    public void setAlbumIdStr(String albumIdStr) {
        this.albumIdStr = albumIdStr;
    }

    public String getAlbumStatus() {
        return albumStatus;
    }

    public void setAlbumStatus(String albumStatus) {
        this.albumStatus = albumStatus;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSourcetemd() {
        return sourcetemd;
    }

    public void setSourcetemd(Long sourcetemd) {
        this.sourcetemd = sourcetemd;
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

    public String getItemStructure() {
        return itemStructure;
    }

    public void setItemStructure(String itemStructure) {
        this.itemStructure = itemStructure;
    }



}
