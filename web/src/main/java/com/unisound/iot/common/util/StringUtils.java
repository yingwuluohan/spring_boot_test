package com.unisound.iot.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
	/**
    * SHA1加密
    *
    * @param decript
    * @return
    */
   public static String encryptSHA1(String decript) {
       try {
           MessageDigest digest = MessageDigest.getInstance("SHA-1");
           digest.update(decript.getBytes("UTF-8"));
           byte messageDigest[] = digest.digest();
           // Create Hex String
           StringBuffer hexString = new StringBuffer();
           // 字节数组转换为 十六进制 数
           for (int i = 0; i < messageDigest.length; i++) {
               String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
               if (shaHex.length() < 2) {
                   hexString.append(0);
               }
               hexString.append(shaHex);
           }

           return hexString.toString();

       } catch (Exception e) {
           return "";
       }
   }
	
	/**
	 * 比较返回
	 * @param input
	 * @param def
	 * @return
	 */
	public static String compareRet(String input, String def){
		if(StringUtils.isEmpty(input)){
			return def;
		}
		return input;
	}
	
	
	public String EscapeSpace(String origStr){
		return origStr;
	}
	
	/**
	 * 使用正则表示提取某个字符串中的内容
	 * @param str
	 * @param regex
	 * @return
	 * @throws Exception
	 */
	public static String getContextUseRegex(String str, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while(m.find()){
			return m.group();
		}
		return null;
	}
	
	/**
	 * 删除字符串中的空格
	 * @param str
	 * @return
	 */
	public static String delSpace(String str){	
		if(null != str){
			return str.replaceAll("(?<=[^a-zA-Z<>])\\s+|\\s+(?=[^a-zA-Z<>])", "").trim();
		}
		return null;
	}

	/**
	 * 将null转换为孔子付出""
	 * @param o
	 * @return
	 */
	public static String nullToEmpty(Object o) {
	    if (o == null) {
	    	return "";
	    }
	    else {
	    	return o.toString();
    	}
	}
	
	/**
	 * 判断一个字符串是否为空，字符串为空意味着该字符串为null或者""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || str.trim().length() == 0){
			return true;
		}
		return false;
	}	
	
	/**
	 * 将json对象转换为map对象
	 * @param json
	 * @return
	 */
	public static Map<String, Map<String, String>> json2Map(String json){
		JSONObject jo = JSONObject.parseObject(json);
		Map<String, Map<String, String>> ret = new HashMap<String, Map<String, String>>();
		if(null != jo){
			for(String key : jo.keySet()){
				JSONObject jo0 = JSONObject.parseObject(jo.get(key).toString());
				if(null != jo0){
					if(jo0.size() > 0){
						ret.put(key, json2SimpleMap(jo0));
					}else{
						ret.put(key, null);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * 将json对象转换成简单的map对象
	 * @param jo
	 * @return
	 */
	public static Map<String, String> json2SimpleMap(JSONObject jo){
		Map<String, String> ret = new HashMap<String, String>();
		for(String key : jo.keySet()){
			ret.put(key, jo.getString(key));
		}
		return ret;
	}
	
	/**
	 * 将String对象转换成简单的map对象
	 * @param jo
	 * @return
	 */
	public static Map<String, String> json2SimpleMap(String str){
		JSONObject jo0 = JSONObject.parseObject(str);
		return json2SimpleMap(jo0);
	}
	
	/**
	 * 判断某个字符串是否包含某个正则表达式匹配的内容，如果包含则返回匹配的起始位置，否则返回-1
	 * @param input
	 * @param reg
	 * @return
	 */
	public static int match(String input, String reg){
		Pattern r = Pattern.compile(reg);
		Matcher m = r.matcher(input);
		if(m.find()){
			String tag = m.group();
			return input.indexOf("<_" + tag + "_>");
		}
		return -1;
	}
	
	/**
	 * 在字符串input的index位置插入cont字符串
	 * @param input
	 * @param cont
	 * @param index
	 * @return
	 */
	public static String insert(String input, String cont, int index){
		if(index < 0 || index > input.length()){
			return input;
		}
		return input.substring(0, index) + cont + input.substring(index, input.length());
	}
	
	/**
	 * 建一个x=a;y=b的字符串转换为一个map
	 * @param str
	 * @return
	 */
	public static Map<String, String> str2Map(String str){
		if(isEmpty(str)){
			return null;
		}
		str = delSpace(str);
		Map<String, String> ret = new HashMap<String, String>();
		String[] paras = str.split(";");
		for(int i =0 ; i < paras.length; i++){
			String[] para = paras[i].split("=");
			if(para.length < 2 || isEmpty(para[0])){
				continue;
			}
			ret.put(para[0].trim(), para[1].trim());
		}
		return ret;
	}
	

	public static String map2Str(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for(String key : map.keySet()){
			if(isEmpty(key)){
				continue;
			}
			if(sb.length() > 0){				
				sb.append(";");
			}
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断字符串是否为一个带port的host
	 * @param host
	 * @return
	 */
	public static boolean isHostAddress(String host){
		String[] ip_port = host.split(":");
		if(ip_port.length == 2){
			String ip = ip_port[0];
			String port = ip_port[1];
			try {
				Integer.parseInt(port);
				return isIPAddress(ip);
			} catch (NumberFormatException e) {
				return false;
			}
		}		
		return false;
	}
	
	/**
	 * 检测一个字符串是否是合法的IP
	 * @param ip
	 * @return
	 */
	public static boolean isIPAddress(String ip){
		String[] ss = ip.split("\\.");
		if(ss.length == 4){
			try{
				for(int i = 0; i < ss.length; i++){
					String s = ss[i];
					int n = Integer.parseInt(s);
					if(n < 0 || n > 255){
						return false;
					}
				}
				return true;
			} catch (NumberFormatException e){
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 获取一个流的字节数据
	 * @param in
	 * @return
	 */
	public static byte[] stream2Bytes(InputStream in) throws Exception{	
		if(null == in){
			return null;
		}
		byte[] bytes = null;
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
	}


	public static Integer string2Integer(String value) {
		if(isEmpty(value)){
			return null;
		}else{
			try {
				return Integer.parseInt(value.trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static long toLong(String string) {
		if(isEmpty(string)){
			return 0;
		}else{
			try {
				return Long.parseLong(string.trim());
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	//序列化为byte[]
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			byte[] b = bos.toByteArray();
			return b;
		} catch (IOException e) {
			logger.error("序列化失败 Exception:" + e.toString());
			return null;
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException ex) {
				logger.debug("序列化失败 Exception:" + ex.toString());
			}
		}
	}

	//反序列化为Object
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.error("bytes Could not deserialize:" + e.toString());
			return null;
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
			} catch (IOException ex) {
				logger.debug("LogManage Could not serialize:" + ex.toString());
			}
		}
	}


}
