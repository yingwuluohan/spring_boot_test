package com.unisound.iot.controller.jdk.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转写处理
 * @author	xiezhb
 * @date	2018年12月21日
 */
public class Transcribe implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(Transcribe.class);
	private AudioTransfer transfer = null;

	/**
	 * 
	 * @param transfer
	 */
	public Transcribe(AudioTransfer transfer) {
		this.transfer = transfer;
	}
	
	@Override
	public void run() {

		try {
			transfer.transfer();			
		} catch ( Exception e) {
			logger.error(e.getMessage(), e);

		}


	}

}
