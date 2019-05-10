package com.unisound.iot.copy_asr.service;


import com.unisound.iot.copy_asr.api.Context;
import com.unisound.iot.copy_asr.api.Engine;
import com.unisound.iot.copy_asr.api.Resource;
import com.unisound.iot.copy_asr.entity.EngineTimeCount;
import com.unisound.iot.copy_asr.factory.ServiceEngine;

/**
 * Asr服务的流程化，分别为startSession..

 */
public class AsrResource implements Resource {
	private final static int ASR_ENGINE_GRAMMAR = 0;
	private final static int ASR_ENGINE_VOCAB = 1;
	//private final static int ASR_ENGINE_MODEL = 2;
   
	//private final static int ASR_ENGINE_EXTRA_DATA_OWNER_SERVICE = 0;
	private final static int ASR_ENGINE_EXTRA_DATA_OWNER_APP = 1;
	//private final static int ASR_ENGINE_EXTRA_DATA_OWNER_USER = 2;
    
	private static final int NORMAL = 0;
	private static final int ERROR = -1;
	
	private static final Object VAD_STOP_HEADER = "VAD_STOP";
	
	//资源引擎负责实际的执行动作
	private ServiceEngine engine = null;

	//当前请求的ID
	private String sessionID = null;	
	

	
	private int status = NORMAL;
	
	private int pcmLen = 0;		
	
	private String lang = "cn";
			
	//是否检测到服务端 vad
	private boolean isVadStop = false;

	//服务端 vad 在最后一次 probe 时候就会调用 end 请求，并将结果保存在此处，当用户调用 stop 的时候，不进行实际的 stop 处理，直接返回结果
	private String stopResult = null;
	
	private Object vadLock = new Object();
			
	private StringBuffer fullResult = new StringBuffer();
	
	public AsrResource(String requestId) {
		this.sessionID = requestId;
	}
				
	/**
	 * 启动asr服务，根据请求信息进行识别的初始化

	 */
	@Override
	public synchronized void startSession(Context context) throws Exception{
		System.out.println(" ******asr start session********:" + sessionID);
		int ret = 2;
		System.out.println("[" + sessionID + "]startRet=" + ret);
		if(ret != 0){
			this.status = ERROR;
			if(null != engine){
				releaseEngine();
			}
		}
	}
	
	/**
	 * 语音识别
	 */
	@Override
	public synchronized byte[] probeSession(Context context) throws Exception{
		System.out.println("asr probe session:" + sessionID);

		
		byte[] decodeData = (byte[])context.getInput();
		
		if(null == decodeData || decodeData.length == 0){
			System.out.println("asr probe session: decodeData is null, sessionID=" + sessionID);
			return null;
		}
		
		pcmLen += decodeData.length;
		

		
		//执行识别动作并获取执行结果
		if(NORMAL == engine.engineSessionRecognize(decodeData, 0)){
			String ret = engine.engineSessionGetResult();
			System.out.println("[" + context.getId() + "]get result: " + ret);
		}
	
		return null;
	}

	/**
	 * vad 时候直接调用 stop
	 */
	private void vadStop() {
		return;
//		isVadStop = true;
//		CommonExecutor.execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {	
//					LOG.debug("[" + sessionID + "]vad stop --> call stop start");
//					byte[] data = null;									
//					
//					if(NORMAL == engine.engineSessionRecognize(data, 1)){
//						stopResult = engine.engineSessionGetResult();
//					}
//					LOG.debug("[" + sessionID + "]vad stop --> call stop end");
//				} catch (Exception e) {
//					LOG.error(e.getMessage(), e);
//					LOG.debug("[" + sessionID + "]vad stop --> call stop error");
//				}
//				synchronized (vadLock) {
//					vadLock.notifyAll();
//				}
//			}
//
//		});
	}

	/**
	 * 结束识别
	 */
	@Override
	public synchronized byte[] stopSession(Context context) throws Exception{
		System.out.println("asr stop session:" + sessionID);
		String ret = null;
		//运行时间
		long runtime = engine.getCount().getCountMap().get(EngineTimeCount.ENGINE_ALL_TIME);
		long engineTime = engine.engineSessionCTime();
		if(0 == engineTime){
			engineTime = runtime;
		}
		if(null != ret){
			return setContext(context,   runtime, engineTime);
		}
			
		return null;
	}
	
	/**
	 * 本方法只能给 stop 调用，stop 时的 vad_stop 一定为 false
	 * @param context
	 * @param
	 * @param runtime
	 * @param engineTime
	 * @return
	 */
	private synchronized byte[] setContext(Context context,   Long runtime, Long engineTime){
		return setContext(context,   runtime, engineTime, false);
	}
	
	/**
	 * 根据运行结果设置context
	 * @param context
	 * @param
	 * @param runtime
	 * @param vad_stop 
	 * @return
	 */
	private synchronized byte[] setContext(Context context,   Long runtime, Long engineTime, boolean vad_stop) {
		String str = "synchronized byte[] setContext";
		return str.getBytes() ;
	}

	/**
	 * 关联某个服务
	 */
	@Override
	public void relateEngine(Context context)  throws Exception{
		ServiceEngine asrEngine = new ServiceEngine(context.getId());
		asrEngine.setRequestId(context.getId());
		
		if(Engine.NORMAL == asrEngine.engineSessionInit( "asr")){
			engine = asrEngine;		
		}else{
			System.out.println( "exception********************" );;
		}
	}

	/**
	 * 释放某个服务
	 */
	@Override
	public void releaseEngine() throws Exception{
		System.out.println("asr release engine : sessionID=" + sessionID);

	}
	
	/**
	 * 个性化服务
	 */
	@Override
	public void personalEngine(Context context) throws Exception {

	}
	
	/**
	 * 根据用户id和appKey进行个性化
	 * @param
	 * @param
	 * @return
	 * @throws Exception 
	 */
	private boolean personalize() throws Exception{		
		if(engine == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 20180416： 个性化数据下沉兼容性处理，
	 * 如果是旧版的thrift，即版本号小于1.2.0的thrift，
	 * 不下发个性化数据（因为为redisKey，下发会引发引擎崩溃）
	 * @return
	 */
	public boolean isOldEngine(){
		return true;
	}
	
	/**
	 * 为兼容旧版的 thrift 根据 redisKey 的协议信息，从 redis 中获取数据
	 * @param keyData
	 * @return
	 */
	public byte[] getData(byte[] keyData){		
		byte type = keyData[0];
		
		//int expire = (keyData[4] & 0xFF | (keyData[3] & 0xFF) << 8 | (keyData[2] & 0xFF) << 16 | (keyData[1] & 0xFF) << 24);
		
		String field = new String(keyData, 6, keyData[5]);
		
		int keyStart = 2 + keyData[5] + 4;
		
		String redisKey = new String(keyData, keyStart, keyData.length - keyStart);
		return null;
	}
		
	public ServiceEngine getEngine() {
		return engine;
	}
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}



	@Override
	public String getId() {
		return sessionID;
	}

}
