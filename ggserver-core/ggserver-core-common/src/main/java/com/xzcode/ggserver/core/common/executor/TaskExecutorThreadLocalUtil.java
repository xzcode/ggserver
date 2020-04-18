package com.xzcode.ggserver.core.common.executor;

import io.netty.util.concurrent.FastThreadLocal;
/**
 * 任务执行器线程线程工具
 * 
 * @author zai
 * 2020-01-19 17:10:12
 */
public class TaskExecutorThreadLocalUtil {
	private static final FastThreadLocal<TaskExecutor> THREAD_LOCAL = new FastThreadLocal<>();
	
	public static TaskExecutor getTaskExecutor() {
		return THREAD_LOCAL.get();
	}
	
	public static void setTaskExecutor(TaskExecutor taskExecutor) {
		if (!THREAD_LOCAL.isSet()) {
			THREAD_LOCAL.set(taskExecutor);			
		}
	}
}
