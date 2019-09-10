package com.unisound.iot.controller.jdk.nio;


import com.unisound.iot.controller.jdk.nio.transfer_mp3_test.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 语音切分
 * @author	xiezhb
 * @date	2018年12月19日
 */
public class AudioRecognize {
	private static final Logger logger = LoggerFactory.getLogger(AudioRecognize.class);
	//设置语音格式
	private static final String USC_AUDIO_FORMAT = "usc_audio_format";
	
	//设置识别的领域，使用领域编号，各个领域编号之间用逗号分隔
	private static final String USC_SERVICE = "usc_service";
	
	//设置远近讲
	private static final String USC_ACOUSTIC = "usc_acoustic";
	
	//设置采样率
	private static final String USC_SAMPLE_RATE = "usc_sample_rate";

	//设置数字显示是否为小写
	private static final String USC_POST_PROC = "usc_post_proc";
	private static final String USC_POST_PROC_TRUE = "true";
	//pcm 语音格式的编码为 1
	private static final String USC_AUDIO_FORMAT_PCM = "1";
	
	//近讲的编号
	private static final String USC_ACOUSTIC_NEAR = "1";
	
	private static final String USC_SAMPLE_RATE_16K = "1";
	
	private static final int THRIFT_DATA_LENGTH = 9600;

	private static final int THRIFT_DATA_SAMPLERATE = 16000;

	private static final int LAST_FLAG = 1;
	
	private static final int NOT_LAST_FLAG = 0;

	private static final String USC_SCENE_INFO = "usc_scene_info";

	/**
	 * 执行语音识别
	 * @param data
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static List recognize(byte[] data, RequestParam param) throws Exception{
		if(null == data || data.length == 0){
			return null;
		}

//		if(TranscribeConf.fake){
//			return TranscribeConf.fake_result;
//		}


		try {


			ByteArrayInputStream input = new ByteArrayInputStream(data);

			byte[] buff = new byte[THRIFT_DATA_LENGTH];
			/** */
			long perTime = THRIFT_DATA_LENGTH / THRIFT_DATA_SAMPLERATE / 2 * 1000;
			int len = 0;
			int i=0;
			while((len = input.read(buff)) > 0){
				i++;

				// 每次传入时间 300ms

				Thread.sleep(50);
			}




			return new ArrayList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ArrayList();
	}



}
