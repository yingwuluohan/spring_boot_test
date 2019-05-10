package com.unisound.iot.copy_asr.factory;



import com.unisound.iot.copy_asr.api.Engine;
import com.unisound.iot.copy_asr.entity.EngineTimeCount;

public class ServiceEngine {
	public static String asrType = Engine.BASE_ENGINE;
	
    private static long asrBase=0;
    private long asrSession=0;
    
    private Engine engine = null; 
    
    private String requestId = "";
    
    //统计运行时间
    private EngineTimeCount count  = new EngineTimeCount();
       
    public void test(String audioFile){   
    	return;
    }
    
    public ServiceEngine(String requestId){

    }
	public static Engine getEngine(String engineType){
		if(Engine.THRIFT_ENGINE.equals(engineType)){
			return null;//new ThriftEngine();
		}else if(Engine.FAKE_ENGINE.equals(engineType)){
			return null;// new FakeEngine();
		}else{
			return new AsrEngine();
		}
	}
    /**
     * 启动asr引擎，需要传入模型的配置文件和启动的引擎数量
     * @param
     * @param num
     * @return
     */
    public static int engineInit(String configStr, int num) throws Exception,Error{
    	Engine engine =  getEngine(asrType);
		asrBase = engine.asrInit(configStr,num);
		if(asrBase==0){
			System.out.println("asr engine init fail");
		    return Engine.CREATE_BASE_FAIL;
		}
		System.out.println("asr engine init success");
        return Engine.NORMAL;
    }

    public void engineDestroy(){
		System.out.println("engineDestroy");
        engine.asrDestroy(asrBase);
    }
    
    public long engineSessionCTime(){
        return engine.asrSessionGetCTime(asrSession);
    }
    
    /**
     * 初始化一个引擎session用于服务用户
     * @return
     */
    public synchronized int engineSessionInit(Object param) throws Exception{
		System.out.println("asr engine session init....");
		asrSession = engine.asrSessionInit(asrBase, param);
		if(asrSession==0){
			System.out.println("asr engine session init fail");
		    return Engine.CREATE_SESSION_FAIL;
		}
		System.out.println("asr engine session init success");
		
        return Engine.NORMAL;
    }

    /**
     * 启动一个引擎服务用户服务用户
     * @param parameter
     * @param userData
     * @return
     */
    public synchronized int engineSessionStart(String parameter, byte[] userData) throws Exception{ 
    	int ret = 0;
    	count.countStart(EngineTimeCount.ENGINE_START_TIME);
		ret = engine.asrSessionStart(asrSession, parameter, userData);
		count.countEnd(EngineTimeCount.ENGINE_START_TIME);
        return ret;
    }

    /**
     * 语音识别
     * @param buffer
     * @param lastSectFlag
     * @return
     */
    public synchronized int engineSessionRecognize(byte[] buffer, int lastSectFlag) throws Exception{
    	int len = 0;
    	if(buffer!=null)
    		len = buffer.length;
		System.out.println("engine session recognize..., lastSectFlag is "+lastSectFlag+" buffer len is "+len);
        int stat = Engine.FATAL_ERROR;	
        count.countStart(EngineTimeCount.ENGINE_ACTION_TIME);      
		stat = engine.asrSessionRecognize(asrSession, buffer, lastSectFlag);        
		count.countEnd(EngineTimeCount.ENGINE_ACTION_TIME);		       
        return stat;
    }

    /**
     * 获取识别结果
     * @return
     */
    public synchronized String engineSessionGetResult() throws Exception{
		System.out.println("engine session get result ...");
        count.countStart(EngineTimeCount.ENGINE_ACTION_TIME);        
        byte[] result = engine.asrSessionGetResult(asrSession);
        count.countEnd(EngineTimeCount.ENGINE_ACTION_TIME);       
        String fmtResult=null;
        
    	if(result!=null){
        	fmtResult= new String(result,"UTF-8");        	
    	}
    	
        return fmtResult;
    }

    /**
     * 添加领域模型
     * @param serverID
     * @return
     */
    public synchronized int engineSessionAddServiceID(int serverID) throws Exception{
		System.out.println("engine session add service id : serverId=" + serverID);
    	
		if(asrSession!=0){
			count.countStart(EngineTimeCount.ENGINE_START_TIME);
			int ret =  engine.asrSessionAddServiceID(asrSession, serverID);
			count.countEnd(EngineTimeCount.ENGINE_START_TIME);
			return ret;
		}
		
    	return Engine.FATAL_ERROR;
    }

    public synchronized int engineSessionSetPostProc(boolean postProc) throws Exception{
    	if(asrSession!=0){   
    		count.countStart(EngineTimeCount.ENGINE_START_TIME);
    		int ret = engine.asrSessionSetPostProc(asrSession, postProc);
    		count.countEnd(EngineTimeCount.ENGINE_START_TIME);
    		return ret;
    	}else{
    		return Engine.FATAL_ERROR;
    	}
    }

    /**
     * 添加个性化数据
     * @param dataOwner
     * @param dataType
     * @param data
     * @return
     */
    public synchronized int engineSessionSetExtraData(int dataOwner, int dataType, byte[] data) throws Exception{
    	if(null == data){
    		return Engine.FATAL_ERROR;
    	}
		System.out.println("engine session set extra data:dataOwner=" + dataOwner + ";dataType=" + dataType + ";dataLength=" + data.length);
    	if(asrSession!=0){		
    		count.countStart(EngineTimeCount.ENGINE_START_TIME);
			int ret = engine.asrSessionSetExtraData(asrSession, dataOwner, dataType, data);
			count.countEnd(EngineTimeCount.ENGINE_START_TIME);
			return ret;
    	}
    	return Engine.FATAL_ERROR;
    }   
    
    /**
     * 远近讲
     * @param acouticID
     * @return
     */
    public synchronized int engineSessionSetAcoustic(int acouticID) throws Exception{
		System.out.println("engine session set acoustic : acouticID=" + acouticID);
    	if(asrSession!=0){	
    		count.countStart(EngineTimeCount.ENGINE_START_TIME);
			int ret = engine.asrSessionSetAcoustic(asrSession, acouticID);
			count.countEnd(EngineTimeCount.ENGINE_START_TIME);
			return ret;			
    	}
    	return Engine.FATAL_ERROR;
    }
    
    /**
     * 设置采样率
     * @param
     * @return
     */
    public synchronized int engineSampleRate(int sampleRate) throws Exception{
		System.out.println("engine session set acoustic : sampleRate=" + sampleRate);
    	if(asrSession!=0){	
    		count.countStart(EngineTimeCount.ENGINE_START_TIME);
			int ret = engine.asrSessionSetSampleRate(asrSession, sampleRate);
			count.countEnd(EngineTimeCount.ENGINE_START_TIME);
			return ret;			
    	}
    	return Engine.FATAL_ERROR;
    }
    
    /**
     * 设置oneshot
     * @param oneshot
     * @return
     * @throws Exception
     */
    public synchronized int engineSessionSetOneshot(String oneshot) throws Exception{
		System.out.println("engine session set oneshot : oneshot=" + oneshot);
    	if(asrSession != 0){
    		int ret = engine.asrSessionSetSceneInfo(asrSession, oneshot);
    		
    		return ret;
    	}
    	
    	return Engine.FATAL_ERROR;
    }
    
    public synchronized void engineSessionRelease() throws Exception{
		System.out.println("engineSessionRelease");
        if(asrSession!=0){
        	count.countStart(EngineTimeCount.ENGINE_END_TIME);
        	engine.asrSessionRelease(asrSession);
			count.countEnd(EngineTimeCount.ENGINE_END_TIME);
        }
    }
    
    public synchronized void engineSessionSetSignalSetting(String sgn_setting) throws Exception{
		System.out.println("signal setting");
         if(asrSession!=0){
         	count.countStart(EngineTimeCount.ENGINE_END_TIME);
         	engine.asrSessionSetSignalSetting(asrSession, sgn_setting);
 			count.countEnd(EngineTimeCount.ENGINE_END_TIME);
         }
    }
    
    public EngineTimeCount getCount(){
		return this.count;
	}   
    
    public static boolean isBaseType(){
    	return Engine.BASE_ENGINE.equals(asrType);
    }

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getVersion(){
		return engine.getVersion();
	}
} 
