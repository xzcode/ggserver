package xzcode.ggserver.core.message.receive.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务线程池
 * 
 * 
 * @author zai
 * 2018-05-29
 */
public class RequestMessageTaskExecutor extends ThreadPoolExecutor{
	
	private static final Logger logger = LoggerFactory.getLogger(RequestMessageTaskExecutor.class);
	
	private final static AtomicInteger threadIndex = new AtomicInteger(1);
	
	private GGServerConfig config;
	
	public RequestMessageTaskExecutor(GGServerConfig config) {
		
		super(config.getReqTaskCorePoolSize(), config.getReqTaskMaxPoolSize(), config.getReqTaskKeepAliveTime(), TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(config.getReqTaskTaskQueueSize()), new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"Socket Server Task Thread - " + threadIndex.getAndIncrement());
			}
		}, 
				
		//任务拒绝策略
		(Runnable r, ThreadPoolExecutor executor) -> {
			
				logger.warn("A Task Is Running At A [Rejected Execution Thread] ! Task:{} ", r);
				
				//新建额外线程执行任务
				new Thread(r, "Socket Server Task [Rejected Execution Thread] - " + threadIndex.getAndIncrement());
		});
		
	}

	public void setConfig(GGServerConfig config) {
		this.config = config;
	}
	
	public GGServerConfig getConfig() {
		return config;
	}
}
