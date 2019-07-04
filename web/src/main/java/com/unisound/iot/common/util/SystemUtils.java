package com.unisound.iot.common.util;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.unisound.iot.controller.jdk.nio.CommonX509TrustManager;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.beans.PropertyVetoException;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


public class SystemUtils {
	private static QueryRunner runner = null;
	private static final Logger logger = LoggerFactory.getLogger(SystemUtils.class);

	public static void initMysql(String drive, String url, String user, String pwd) {
		ComboPooledDataSource cpds = new ComboPooledDataSource();

		try {
			cpds.setDriverClass(drive);
		} catch (PropertyVetoException e) {
			logger.error(e.getMessage(), e);
		} //loads the jdbc driver

		cpds.setJdbcUrl(url);
		cpds.setUser(user);
		cpds.setPassword(pwd);

		cpds.setMinPoolSize(5);
		cpds.setMaxPoolSize(20);

		cpds.setAutomaticTestTable("TR_BASE_TB");
		cpds.setIdleConnectionTestPeriod(60);
		cpds.setTestConnectionOnCheckin(true);

		cpds.setCheckoutTimeout(4000);

		cpds.setAcquireRetryAttempts(3);
		cpds.setAcquireRetryDelay(20000);
		cpds.setAcquireIncrement(1);
		cpds.setMaxStatements(0);
		cpds.setMaxStatementsPerConnection(0);

		runner = new QueryRunner(cpds);
	}


	public synchronized static QueryRunner getDefaultRunner(){
		return runner;
	}

	public static void writeData2File(byte[] data, String file){
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.flush();
			out.close();		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public static List<String> readFormFile(String file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		
		List<String> ret = new ArrayList<String>();
		while((line = br.readLine()) != null){
			if(!StringUtils.isEmpty(line)){
				ret.add(line);
			}
		}
	
		br.close();
		
		return ret;
	}
	
	/**
	 * 加载文件
	 * @param filePath
	 */
	public static void loadLibs(String filePath){
		File[] libs = new File(filePath).listFiles();
		if(null != libs){
			for(File f : libs){
				logger.debug("load " + f.getName());
				System.load(f.getAbsolutePath());
				logger.debug("load " + f.getName() + " success");
			}
		}
	}
	
	/**
	 * 加载文件
	 * @param fileName
	 */
	public static void loadLibVarLibName(String fileName){
		String[] libs = fileName.split(",");
		for(int i = 0; i < libs.length; i++){
			try{
				logger.debug("load " + libs[i]);
				System.load(libs[i]);
				logger.debug("load " + libs[i] + " success");
			}catch(Exception e){
				logger.error("load " + libs[i] + "error",e);
			}
		}
	}

	
	/**
	 * 获取绝对路径
	 * @param file
	 * @return
	 */
	public static String absolute(String file){
		return  SystemUtils.class.getClassLoader().getResource(file).getFile();
	}
	
	/**
	 * 执行运行系统中的命令
	 * @param cmd
	 * @return
	 */
	public static List<String> runCmd(String cmd){
		InputStream in = null;  
		String[] cmdA = {"/bin/bash","-c",cmd};
        try {  
            Process pro = Runtime.getRuntime().exec(cmdA);  
            pro.waitFor();  
            in = pro.getInputStream();  
            BufferedReader read = new BufferedReader(new InputStreamReader(in)); 

            List<String> ret = new ArrayList<String>();
            
            String line = null;
            while((line = read.readLine()) != null){
            	ret.add(line);
            }
            return ret; 
        } catch (Exception e) {  
            logger.error("runCmd " + cmd + " error", e);
        }
        return null;
	}
	
	/**
	 * 加载性别识别的so文件
	 * @param path
	 */
	public static void loadSpeakerLib(String path){		
	    System.load(path + "libspeakerEngineJni.so");
	}
	
	/**
	 * 加载asr的so文件
	 * @param path
	 */
	public static void loadAsrLib(String path){				
        System.load(path+"/libasrEngineJni.so");
	}
	
	/**
	 * 获取asr redis缓存的key
	 * @param masterOwnerId
	 * @param appKey
	 * @param userId
	 * @param type
	 * @param field
	 * @param subType
	 * @param subInReal
	 * @return
	 */
	public static String getAsrCacheKey(String serviceType, String masterOwnerId, String appKey,
			String userId, String type, String field, String subType,
			String subInReal) {
		StringBuffer sb = new StringBuffer(serviceType + "_");
		sb.append(StringUtils.nullToEmpty(masterOwnerId) + "_");
		sb.append(StringUtils.nullToEmpty(appKey) + "-");
		sb.append(StringUtils.nullToEmpty(userId) + "_");
		sb.append(StringUtils.nullToEmpty(type) + "_");
		sb.append(StringUtils.nullToEmpty(field) + "_");
		sb.append(StringUtils.nullToEmpty(subType) + "_");
		sb.append(StringUtils.nullToEmpty(subInReal));
		return sb.toString();
	}

	/**
	 * 休眠
	 * @param time
	 */
	public static void sleep(int time){
		try {
			//logger.debug("sleep " + time + " ......");
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.error("sleep error", e);
		}
	}
	
	/**
	 * 判断某个文件是否存在
	 * @param file
	 * @return
	 */
	public static boolean isExist(String file){
		File f = new File(file);
		return f.exists();
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] stream2Bytes(InputStream in){	
		
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {					
    			bufferStream.write(buffer, 0, len);
    		}
			buffer = null;			
    		if(bufferStream.toByteArray().length>0){    			
    			bytes = bufferStream.toByteArray();
    		}
    		bufferStream.close();
    		bufferStream = null;
    		in.close();
    		return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] stream2Bytes(InputStream in, int buffL){	
		
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[buffL];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {					
    			bufferStream.write(buffer, 0, len);
    		}
			buffer = null;			
    		if(bufferStream.toByteArray().length>0){    			
    			bytes = bufferStream.toByteArray();
    		}
    		bufferStream.close();
    		bufferStream = null;
    		in.close();
    		return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] httpsPost(String url, byte[] data, Map<String, ? extends Object> headers, int connectionTimeout, int readTimeout) throws Exception{
		SSLContext sslContext = SSLContext.getInstance("SSL");
		
		TrustManager[] tm={new CommonX509TrustManager()};
		//初始化  
		sslContext.init(null, tm, new java.security.SecureRandom());
		
		//获取SSLSocketFactory对象  
		SSLSocketFactory ssf = sslContext.getSocketFactory(); 
		
		logger.debug("https post url: " + url);
		
		OutputStream out = null;		
		if(StringUtils.isEmpty(url)){
			logger.error("url is null, are you kidding me?");
			return null;
		}		
		
		URL uRL = new URL(url);			
		HttpsURLConnection conn = (HttpsURLConnection) uRL.openConnection();
			
		conn.setRequestMethod("POST");
		
		if(connectionTimeout > 0){
			conn.setConnectTimeout(connectionTimeout);
		}
		
		if(readTimeout > 0){
			conn.setReadTimeout(readTimeout);
		}
		
		if(null != headers && headers.size() > 0){
			for(String key : headers.keySet()){
				conn.addRequestProperty(key, headers.get(key) + "");
			}
		}		
		
		conn.setSSLSocketFactory(ssf);
		
		if(null != data){		
			logger.debug("data length: " + data.length);
			
			conn.setDoOutput(true); 
			conn.setDoInput(true); 
			conn.setUseCaches(false); 
			
			out = conn.getOutputStream();
			out.write(data);
			out.flush();
		}
		
		conn.connect();
		
		byte[] retData = stream2Bytes(conn.getInputStream());
		
		if(out != null){
			out.close();
		}
		
		if(null != retData){
			return retData;
		}
		
		return null;
	}
	
	public static String httpGet(String url, boolean printError, int connectionTimeout){
		try {
			URL uRL = new URL(url);
			URLConnection conn = uRL.openConnection();
			conn.setConnectTimeout(connectionTimeout);
			conn.setReadTimeout(100);			
			byte[] data = stream2Bytes(conn.getInputStream());
			if(null != data){
				return new String(data);  
			}
		} catch (Throwable e) {
			if(printError){
				logger.error("", e);
			}
		}
		return null;
	}
	
	public static byte[] httpGet(String url, byte[] sdata, Map<String, Object> requestParam, boolean printError){
		try {
			return httpGet(url, sdata, requestParam, printError, -1, -1);
		} catch (Exception e) {
			if(printError){
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	public static byte[] httpGet(String url, byte[] sdata, Map<String, Object> requestParam, boolean printError, int connectTimeout, int readTimeout) throws Exception{
		OutputStream out = null;		
			
		if(StringUtils.isEmpty(url)){
			logger.error("url is null, are you kidding me?");
			return null;
		}
		if(null != requestParam && requestParam.size() > 0){
			if(!url.endsWith("?")){
				url += "?";
			}
			for(String key : requestParam.keySet()){
				if(requestParam.get(key) != null){
					url += (key + "=" + URLEncoder.encode((String)requestParam.get(key), "utf-8") + "&");
				}
			}
			if(url.endsWith("&")){
				url = url.substring(0, url.length() - 1);
			}
		}
		
		URL uRL = new URL(url);			
		HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
		if(connectTimeout > 0){
			conn.setConnectTimeout(connectTimeout);
		}
		
		if(readTimeout > 0){
			conn.setReadTimeout(readTimeout);
		}
		
		conn.setRequestMethod("GET");			
		
		if(null != sdata){				
			conn.setDoOutput(true); 
			conn.setDoInput(true); 
			conn.setUseCaches(false); 
			
			out = conn.getOutputStream();
			out.write(sdata);
			out.flush();
		}
		
		logger.debug("get url: " + url);
		
		byte[] data = stream2Bytes(conn.getInputStream());
		
		if(out != null){
			out.close();
		}
		
		if(null != data){
			return data;
		}

		return null;
	}
	
	public static byte[] httpPost(String url, byte[] sdata, Map<String, Object> requestParam, boolean printError){				
		try {			
			return httpPost(url, sdata, requestParam, -1, -1);
		} catch (Throwable e) {
			if(printError){
				logger.error("", e);
			}
		}
		return null;
	}
	
	public static byte[] httpPost(String url, byte[] sdata, Map<String, Object> requestParam) throws Exception{
		return httpPost(url, sdata, requestParam, -1, -1);
	}
	
	public static byte[] httpPost(String url, byte[] sdata, Map<String, Object> requestParam, int connectTimeout, int readTimeout) throws Exception{
		OutputStream out = null;		
		if(StringUtils.isEmpty(url)){
			logger.error("url is null, are you kidding me?");
			return null;
		}		
		
		URL uRL = new URL(url);			
		HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
		
		conn.setRequestMethod("POST");
		
		if(connectTimeout > 0){
			conn.setConnectTimeout(connectTimeout);
		}
		
		if(readTimeout > 0){
			conn.setReadTimeout(readTimeout);
		}
		
		if(null != requestParam && requestParam.size() > 0){
			for(String key : requestParam.keySet()){
				conn.addRequestProperty(key, requestParam.get(key) + "");
			}
		}			
		
		if(null != sdata){		
			logger.debug("data length: " + sdata.length);
						
			conn.setDoOutput(true); 
			conn.setDoInput(true); 
			conn.setUseCaches(false); 
			
			out = conn.getOutputStream();
			out.write(sdata);
			out.flush();
		}
		
		conn.connect();
		
		logger.debug("get url: " + url);
		
		byte[] data = stream2Bytes(conn.getInputStream());
		
		if(out != null){
			out.close();
		}
		
		if(null != data){
			return data;
		}
		return null;
	}
	
	public static byte[] httpGet(String url, Map<String, Object> requestParam, Map<String, String> headers) throws Exception{
		return httpGet(url, requestParam, headers, 1000, 1000);
	}
	
	public static byte[] httpGet(String url, Map<String, Object> requestParam, Map<String, String> headers, 
			int connecTimeout, int readTimeout) throws Exception{				
		if(StringUtils.isEmpty(url)){
			logger.error("url is null, are you kidding me?");
			return null;
		}
		if(null != requestParam && requestParam.size() > 0){
			if(!url.endsWith("?")){
				url += "?";
			}
			for(String key : requestParam.keySet()){
				url += (key + "=" + requestParam.get(key) + "&");
			}
			if(url.endsWith("&")){
				url = url.substring(0, url.length() - 1);
			}
		}
		
		URL uRL = new URL(url);			
		HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
		if(null != headers){
			for(String key : headers.keySet()){
				conn.addRequestProperty(key, headers.get(key));				
			}
		}
		
		conn.setConnectTimeout(connecTimeout);
		conn.setReadTimeout(readTimeout);
		conn.setRequestMethod("GET");			
				
		logger.debug("get url: " + url);
		
		byte[] data = stream2Bytes(conn.getInputStream());
		
		if(null != data){
			return data;
		}
		
		return null;
	}

	/**
	 * 发送 https 的 get 请求
	 * @param url
	 * @param data
	 * @param headers
	 * @param b
	 * @param connectionTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static byte[] httpsGet(String url, byte[] data, Map<String, Object> headers, boolean b, int connectionTimeout, int readTimeout) throws Exception {
		SSLContext sslContext = SSLContext.getInstance("SSL");
		
		TrustManager[] tm={new CommonX509TrustManager()};  
		//初始化  
		sslContext.init(null, tm, new java.security.SecureRandom());
		
		//获取SSLSocketFactory对象  
		SSLSocketFactory ssf = sslContext.getSocketFactory(); 
		
		logger.debug("https post url: " + url);
		
		OutputStream out = null;		
		if(StringUtils.isEmpty(url)){
			logger.error("url is null, are you kidding me?");
			return null;
		}		
		
		URL uRL = new URL(url);			
		HttpsURLConnection conn = (HttpsURLConnection) uRL.openConnection();
			
		conn.setRequestMethod("GET");
		
		if(connectionTimeout > 0){
			conn.setConnectTimeout(connectionTimeout);
		}
		
		if(readTimeout > 0){
			conn.setReadTimeout(readTimeout);
		}
		
		if(null != headers && headers.size() > 0){
			for(String key : headers.keySet()){
				conn.addRequestProperty(key, headers.get(key) + "");
			}
		}		
		
		conn.setSSLSocketFactory(ssf);
		
		if(null != data){		
			logger.debug("data length: " + data.length);
			
			conn.setDoOutput(true); 
			conn.setDoInput(true); 
			conn.setUseCaches(false); 
			
			out = conn.getOutputStream();
			out.write(data);
			out.flush();
		}
		
		conn.connect();
		
		byte[] retData = stream2Bytes(conn.getInputStream());
		
		if(out != null){
			out.close();
		}
		
		if(null != retData){
			return retData;
		}
		
		return null;
	}

	/**
	 * 检查文件是否被写入
	 * @param fileName
	 */
	public static void fileCheck(String fileName) {
		File f = new File(fileName);
		long fl_before = f.length();
		long fl_after = 0;
		
		final int CHECK_INTERVAL = 200;
		
		SystemUtils.sleep(CHECK_INTERVAL);
		
		while((fl_after = f.length()) != fl_before){
			fl_before = fl_after;
			SystemUtils.sleep(CHECK_INTERVAL);
		}
	}

	/**
	 * 获取本地IP地址
	 * @return
	 */
	//fitbug:http://zentao.yzs.io/index.php?m=bug&f=view&bugID=7450
	public static boolean LOCAL_INETADDRESS_CHECK = true;
	private static String localIPCache = null;
	public static String getLocalIP(){
		if(localIPCache != null){
			return localIPCache;
		}
		String localIP = "127.0.0.1";
		boolean getIP = false;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				if(getIP==false){
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
							if(!LOCAL_INETADDRESS_CHECK ||inetAddress.isSiteLocalAddress()){
								String tmpIP = inetAddress.getHostAddress().toString();

								if(tmpIP!=null&&!tmpIP.equalsIgnoreCase("")&&!isVirtualIP(tmpIP)){
									getIP = true;
									localIP = tmpIP;
									//logger.debug("localIP ip is "+localIP);
								}
								break;
							}
						}
					}
				}else
					break;
			}


			logger.debug("localIP is: "+localIP);

		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		localIPCache = localIP;
		return localIP;
	}

	/**
	 *
	 * @param ip
	 * @return
	 */
	private static boolean isVirtualIP(String ip){

		if (ip == null){
			return true;
		}

		String[] sub = ip.split("\\.");

		if (sub.length > 3 && !sub[2].equalsIgnoreCase("0")){
			return false;
		}

		return true;
	}

	public final static String MD5(String s) {
		return MD5(s, null);
	}

	public static String getMd5Hex(InputStream inputStream) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		long audioLen = 0;

		try {
			byte[] buffer = new byte[2048];
			int read = inputStream.read(buffer);
			while (read > -1) {
				audioLen += read;
				// 计算MD5
				digest.update(buffer, 0, read);

				read = inputStream.read(buffer);
			}
		} catch (Exception e) {

		} finally {

		}

		return Hex.encodeHexString(digest.digest());
	}

	public final static String MD5(String s, String charset) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		try {
			byte[] btInput = null;
			if(StringUtils.isEmpty(charset)){
				btInput = s.getBytes();
			}else{
				btInput = s.getBytes(charset);
			}
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
