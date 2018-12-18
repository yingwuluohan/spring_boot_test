package com.unisound.iot.common.modle.dataRpc;/**
 * Created by Admin on 2017/12/26.
 */

import java.io.Serializable;

/**
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2017/12/26
 **/
public class UrlAudioMeta implements Serializable {

    private static final long serialVersionUID = -250578726598958640L;

    private Integer adTime;
    private Integer bitrate;
    private Integer duration;
    private String hearSense;
    private String url;
    private String volume;

    public Integer getAdTime() {
        return adTime;
    }

    public void setAdTime(Integer adTime) {
        this.adTime = adTime;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getHearSense() {
        return hearSense;
    }

    public void setHearSense(String hearSense) {
        this.hearSense = hearSense;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
