package com.unisound.iot.controller.jdk.nio;

import java.util.concurrent.Executor;

/**
 * 负责整个 TR 的任务执行
 * @author	xiezhb
 * @date	2018年11月6日
 */
public class CommonExecutor {
	private static final int MAX_SHARE_THREAD_NUM = 500;
	
	private static Executor executor = null;

	/**
	 * 启动
	 */
	 static  {
		//executor = Executors.newScheduledThreadPool(MAX_SHARE_THREAD_NUM);
	}
	
	/**
	 * 执行请求
	 * @param command
	 */
	public static void execute(Runnable command){
		executor.execute(command);
	}
}
