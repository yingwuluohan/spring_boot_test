package com.unisound.iot.common.vo.album;


import com.unisound.iot.common.vo.BaseVO;

import java.io.Serializable;

/**
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2018/1/9
 **/
public class SoundAlbumTypeVO extends BaseVO implements Serializable {
    private Long albumTypeId;
    private String albumTypeName;
    private Integer currentPage;
    private Integer begin;
    private Integer end;

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getCurrentPage() {
        if( null == currentPage ){
            return 0;
        }
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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
