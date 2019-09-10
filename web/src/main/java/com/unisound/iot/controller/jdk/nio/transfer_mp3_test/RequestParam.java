package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import java.util.List;
import java.util.UUID;

/**
 * 请求参数

 */
public class RequestParam {

    public final static String FORMAT_WAV = "wav";

    public final static String FORMAT_AMR = "amr";

    public final static String FORMAT_OPUS = "opus";

    public final static String FORMAT_OPUS_HUAWEI = "opus-huawei";

    public final static String FORMAT_M4A = "m4a";

    private String transId = null;

    //识别领域
    private String res;

    //是否使用热词
    private String useHotData = null;

    //个性化数据
    private List<PersonalData> personalDatas = null;

    //上传文件的md5
    private String md5 = null;

    // 重试
    private String retry = null;

    //语音类型
    private String format;

    //用户 id
    private String userId;

    //appkey
    private String appKey;

    //语音文件
    private String fileName;

    //请求的 ip 地址
    private String remoteIP;

    //请求的时间戳
    private Long timestamp;

    //请求签名
    private String sign;

    // 分块上传时块id
    private String  partId;

    // 回调地址
    private String callBackUrl;


    public String getRetry() {
        return retry;
    }

    public void setRetry(String retry) {
        this.retry = retry;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }
    public static String uniqueId(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }

    public RequestParam(){
        setTransId( uniqueId());
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getUseHotData() {
        return useHotData;
    }

    public void setUseHotData(String useHotData) {
        this.useHotData = useHotData;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public List<PersonalData> getPersonalDatas() {
        return personalDatas;
    }

    public void setPersonalDatas(List<PersonalData> personalDatas) {
        this.personalDatas = personalDatas;
    }
    @Override
    public String toString() {
        return "RequestParam [transId=" + transId + ", res=" + res
                + ", format=" + format + ", userId=" + userId + ", appKey="
                + appKey + ", fileName=" + fileName + ", remoteIP=" + remoteIP
                + ", timestamp=" + timestamp + ", sign=" + sign
                + "]";
    }

}
