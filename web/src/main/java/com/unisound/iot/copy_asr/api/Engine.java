package com.unisound.iot.copy_asr.api;

public interface Engine {	
	public final static int NORMAL = 0;
    public final static int CREATE_BASE_FAIL = -1;
    public final static int CREATE_SESSION_FAIL = -2;
    public final static int FATAL_ERROR = -3;
    
	public static final String BASE_ENGINE = "base";
	public static final String THRIFT_ENGINE = "thrift";
	public static final String FAKE_ENGINE = "fake";	
    
	long asrSessionInit(long asrBase, Object param);   
    int asrSessionStart(long session, String parameter, byte[] userData);
    int asrSessionRecognize(long session, byte[] buffer, int lastSectFlag);
    byte[] asrSessionGetResult(long session);
    void asrSessionRelease(long session);
    int asrSessionSetSceneInfo(long session, String value);
    
    int asrSessionSetSampleRate(long session, int samplerateID);
    int asrSessionSetAcoustic(long session, int acouticID);
    
    int asrSessionAddServiceID(long session, int serverID);
    int asrSessionSetPostProc(long session, boolean postProc);
    int asrSessionSetExtraData(long session, int dataOwner, int dataType, byte[] data);
    
    int asrSessionSetSignalSetting(long session, String value);
   
    long asrSessionGetCTime(long session);
    
    long asrInit(String configFileName, int sessionNum);
    void asrDestroy(long asrBase);
    
    String getVersion();
}
