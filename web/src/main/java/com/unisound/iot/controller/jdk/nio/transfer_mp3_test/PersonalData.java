package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;


/**
 * 个性化数据

 */
public class PersonalData {
	public final static String GRAMMAR = "0";
    public final static String VOCAB = "1";
    public final static String MODEL = "2";
   
    public final static String OWNER_SERVICE = "0";
    public final static String OWNER_APP = "1";
    public final static String OWNER_USER = "2";
	
	private byte[] data;
	
	private String dataOwner;
	
	private String dataType;
	
	//保存在 redis 的 key
	private String redisKey;
	
	//数据缓存时间
	private long expireTime;
	
	//所属的用户 id
	private String userId;
	
	//所属的领域
	private String domain;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(String dataOwner) {
		this.dataOwner = dataOwner;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "PersonalData [data=" + data.length + ", dataOwner="
				+ dataOwner + ", dataType=" + dataType + ", redisKey="
				+ redisKey + ", expireTime=" + expireTime + ", userId="
				+ userId + ", domain=" + domain + "]";
	}
	
	
}
