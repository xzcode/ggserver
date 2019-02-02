package com.xzcode.socket.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.config.SocketServerConfig;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务线程池
 * 
 * 
 * @author zai
 * 2018-05-29
 */
public class SocketServerTaskExecutor extends ThreadPoolExecutor{
	
	private static final Logger logger = LoggerFactory.getLogger(SocketServerTaskExecutor.class);
	
	private final static AtomicInteger threadIndex = new AtomicInteger(1);
	
	private SocketServerConfig config;
	
	public SocketServerTaskExecutor(SocketServerConfig config) {
		
		super(config.getCorePoolSize(), config.getMaximumPoolSize(), config.getKeepAliveTime(), TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(config.getTaskQueueSize()), new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"Socket Server Task Thread - " + threadIndex.getAndIncrement());
			}
		}, 
		//任务拒绝策略
		new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				logger.warn("A Task Is Running At A [Rejected Execution Thread] ! Task:{} ", r);
				new Thread(r, "Socket Server Task [Rejected Execution Thread] - " + threadIndex.getAndIncrement());
			}
			
		});
		
	}

	public void setConfig(SocketServerConfig config) {
		this.config = config;
	}
	
	public SocketServerConfig getConfig() {
		return config;
	}
}
