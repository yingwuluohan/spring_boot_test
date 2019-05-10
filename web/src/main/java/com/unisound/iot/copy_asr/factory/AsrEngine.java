package com.unisound.iot.copy_asr.factory;



import com.unisound.iot.copy_asr.api.Engine;

public class AsrEngine implements Engine {
	/**
	 *	加载so文件

	 */
    static{
 	   try{   
 		   String libPaths = System.getProperty("java.library.path");
		   System.out.println("java.library.path = " + libPaths);
 		   String[] libPathArray = libPaths.split(":");
 		   for(String libPath : libPathArray){
	 			try {

					System.out.println("load asr libasrEngineJni.so file success, libPath=" + libPath);
	 				break;
				} catch (Exception e) {
					e.printStackTrace();
				}
 		   }
 		} catch (Exception e) {
		   System.out.println("load asr libasrEngineJni.so file failed" );
 		}
    }
    
    public long asrSessionInit(long asrBase, Object param){
    	return asrSessionInit(asrBase);
    }

	/**
	 *
	 * @param asrBase
	 * @return
	 */
	public native long asrSessionInit(long asrBase);

	/**
	 *
	 * @param session
	 * @param parameter
	 * @param userData
	 * @return
	 */
    public native int asrSessionStart(long session, String parameter, byte[] userData);

	/**
	 *
	 * @param session
	 * @param buffer
	 * @param lastSectFlag
	 * @return
	 */
    public native int asrSessionRecognize(long session, byte[] buffer, int lastSectFlag);

	/**
	 *
	 * @param session
	 * @return
	 */
	public native byte[] asrSessionGetResult(long session);

	/**
	 *
	 * @param session
	 */
    public native void asrSessionRelease(long session);

	/**
	 *
	 * @param session
	 * @param value
	 * @return
	 */
    public native int asrSessionSetSceneInfo(long session, String value);

	/**
	 *
	 * @param session
	 * @param acouticID
	 * @return
	 */
    public native int asrSessionSetAcoustic(long session, int acouticID);

	/**
	 *
	 * @param session
	 * @param serverID
	 * @return
	 */
    public native int asrSessionAddServiceID(long session, int serverID);

	/**
	 *
	 * @param session
	 * @param postProc
	 * @return
	 */
    public native int asrSessionSetPostProc(long session, boolean postProc);
    public native int asrSessionSetExtraData(long session, int dataOwner, int dataType, byte[] data);
    
    public native int asrSessionSetSignalSetting(long session, String value);
    
    public native long asrInit(String configFileName, int sessionNum);
    public native void asrDestroy(long asrBase);
    public native long asrSessionGetCTime(long session);
	
	public native int asrSessionSetSampleRate(long session, int samplerateID);

	@Override
	public String getVersion() {
		return "";
	}

}
