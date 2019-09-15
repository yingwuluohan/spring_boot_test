package com.unisound.iot.controller.jdk.nio;

import com.unisound.iot.controller.jdk.nio.transfer_mp3_test.RequestParam;

/**
 * 保存转写结果

 */
public class TranscribeResult {
	public static final String  AUDIO_CHUNK_INDEX = "index";
	public static final String  AUDIO_CHUNK_START_TIME = "start";
	public static final String  AUDIO_CHUNK_END_TIME = "end";
	public static final String  AUDIO_CHUNK_TEXT = "text";
	public static final String AUDIO_CHUNK_TEXT_LENGTH = "text_length";

	private String appkey = null;
	private String userid = null;
	private RequestParam param = null;
	private long startTime = 0;
	
	private long costTime = 0;
	
	private String result = null;
	
	private long audioDration = 0;
	
	private String transferId;
	
	private int errorCode = 0;
	
	private String errorMsg = null;
	
	private long progress = 0;
	
	private String status = null;
	

	
	private String audioStorageUrl = null;
	
	private int textLength = 0;

	private String callBackUrl = null;

	private boolean useHotData = false;

	private int tryTimes = 1;


	public RequestParam getParam() {
		return param;
	}

	public void setParam(RequestParam param) {
		this.param = param;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}


	public boolean isUseHotData() {
		return useHotData;
	}

	public void setUseHotData(boolean useHotData) {
		this.useHotData = useHotData;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	
	public TranscribeResult(String transferId){
		this.transferId = transferId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getAudioDration() {
		return audioDration;
	}

	public void setAudioDration(long audioDration) {
		this.audioDration = audioDration;
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		if(audioDration > 0 && progress > audioDration){
			this.progress = audioDration;
		}else{
			this.progress = progress;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public int getTextLength() {
		return textLength;
	}

	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}

	public String getAudioStorageUrl() {
		return audioStorageUrl;
	}

	public void setAudioStorageUrl(String audioStorageUrl) {
		this.audioStorageUrl = audioStorageUrl;
	}
}
