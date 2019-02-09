package xzcode.ggserver.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.executor.task.TimeoutInvokeTask;
import xzcode.ggserver.core.executor.task.TimeoutRunnable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务线程执行器
 * 
 * 
 * @author zai
 * 2018-05-29
 */
public class GGServerTaskExecutor extends ScheduledThreadPoolExecutor{
	
	private static final Logger logger = LoggerFactory.getLogger(GGServerTaskExecutor.class);
	
	private final static AtomicInteger threadIndex = new AtomicInteger(1);
	
	private GGServerConfig config;
	
	public GGServerTaskExecutor(GGServerConfig config) {
		super(config.getReqTaskCorePoolSize());
		this.config = config;
				
		//任务拒绝策略
		this.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor executor) -> {
			
			logger.warn("A Task Is Running At A [Rejected Execution Thread] ! Task:{} ", r);
			
			//新建额外线程执行任务
			new Thread(r, "GGServer Task [Rejected Execution Thread] - " + threadIndex.getAndIncrement());
		});
		
		//设置线程工厂
		this.setThreadFactory((Runnable r) -> {
			return new Thread(r,"Socket Server Task Thread - " + threadIndex.getAndIncrement());
		});
		
		this.setKeepAliveTime(this.config.getReqTaskKeepAliveTime(), TimeUnit.MILLISECONDS);
		
		
	}
	
	/**
	 * 设置超时任务
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai
	 * 2019-02-09 18:42:03
	 */
	public ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec) {
		TimeoutInvokeTask task = new TimeoutInvokeTask(runnable);
		ScheduledFuture<?> future = this.scheduleWithFixedDelay(task, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		task.setFuture(future);
		return future;
	}
	
	/**
	 * 设置超时任务
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai
	 * 2019-02-09 18:42:22
	 */
	public ScheduledFuture<?> setTimeout(TimeoutRunnable runnable, long timeoutMilliSec) {
		TimeoutInvokeTask task = new TimeoutInvokeTask(runnable);
		ScheduledFuture<?> future = this.scheduleWithFixedDelay(task, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		task.setFuture(future);
		runnable.setTimeoutFuture(future);
		runnable.setTimeoutMillisec(timeoutMilliSec);
		return future;
	}

}
