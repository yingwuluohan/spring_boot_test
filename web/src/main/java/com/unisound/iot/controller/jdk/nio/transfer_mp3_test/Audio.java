package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;


import com.unisound.iot.controller.jdk.nio.AudioTransfer;

public class Audio {
	//语音所属的转写文件
	private String tansferFile;
	
	//语音在该转写文件中的编号
	private int audioNum;
	
	private long duration = 0;
	
	private byte[] data = null;

	// 该语音片段在整个语音的起始时间
	private long startTime = 0;
	// 该语音片段在整个语音的结束时间
	private long endTime = 0;

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取语音数据
	 * @return
	 */
	public byte[] getData(){
		return data;
	}
	
	public void setData(byte[] data){
		this.data = data;
		this.duration = data.length / AudioTransfer.ONE_MS_LENGTH;
	}
	
	/**
	 * 设置语音对应的识别结果	
	 * @param text
	 */
	public void setText(String text){
		
	}

	public int getAudioNum() {
		return audioNum;
	}

	public void setAudioNum(int audioNum) {
		this.audioNum = audioNum;
	}

	public String getTansferFile() {
		return tansferFile;
	}

	public void setTansferFile(String tansferFile) {
		this.tansferFile = tansferFile;
	}	

	public long getDuration() {
		return duration;
	}


	@Override
	public String toString() {
		return "Audio{" +
				"tansferFile='" + tansferFile + '\'' +
				", audioNum=" + audioNum +
				", duration=" + duration +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}
	
}
