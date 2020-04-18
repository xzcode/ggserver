package com.xzcode.ggserver.core.common.executor.task;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步可回调任务
 * 
 * 
 * @author zai
 * 2019-12-01 16:00:55
 */
public class AsyncCallableTask<V> implements Callable<V>{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AsyncCallableTask.class);
	
	private Callable<V> callable;
	
	public AsyncCallableTask() {
		
	}
	
	public AsyncCallableTask(Callable<V> callable) {
		this.callable = callable;
	}

	@Override
	public V call() throws Exception {
		try {
			return callable.call();
		} catch (Exception e) {
			LOGGER.error("AsyncCallableTask ERROR!!", e);
		}
		return null;
	}
}
