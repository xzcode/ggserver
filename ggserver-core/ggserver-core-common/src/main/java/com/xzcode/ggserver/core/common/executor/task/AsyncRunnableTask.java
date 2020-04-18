package com.xzcode.ggserver.core.common.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步执行器
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class AsyncRunnableTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AsyncRunnableTask.class);
	
	private Runnable runnable;
	
	public AsyncRunnableTask() {
		
	}
	
	public AsyncRunnableTask(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void run() {
		try {
			runnable.run();
		} catch (Exception e) {
			LOGGER.error("GGTask ERROR!!", e);
		}
	}
}
