package xzcode.ggserver.core.timeout;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.timeout.factory.TimeoutTaskThreadFactory;
import xzcode.ggserver.core.timeout.task.TimeoutInvokeTask;
import xzcode.ggserver.core.timeout.task.TimeoutRunnable;

public class TimeoutTaskManager {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeoutTaskManager.class);
	
	private GGServerConfig config;
	
	private final static AtomicInteger threadIndex = new AtomicInteger(1);
	
	private final TimeoutTaskThreadFactory threadFactory = new TimeoutTaskThreadFactory("Timeout Task Thread");
	
	
	/**
	 * 定时任务执行服务
	 */
	private ScheduledExecutorService executorService;


	public TimeoutTaskManager(GGServerConfig config) {
		super();
		this.config = config;
		
		this.executorService = new ScheduledThreadPoolExecutor(config.getReqTaskCorePoolSize(), threadFactory, 
		//任务拒绝策略
		(Runnable r, ThreadPoolExecutor executor) -> {
			
				logger.warn("A Timeout Task Is Running At A [Rejected Execution Thread] ! Task:{} ", r);
				
				//新建额外线程执行任务
				new Thread(r, "Timeout Task [Rejected Execution Thread] - " + threadIndex.getAndIncrement());
		});
	}
	
	public ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec) {
		TimeoutInvokeTask timeoutInvokeTask = new TimeoutInvokeTask(runnable);
		ScheduledFuture<?> future = this.executorService.scheduleWithFixedDelay(timeoutInvokeTask, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		timeoutInvokeTask.setFuture(future);
		return future;
	}
	
	public ScheduledFuture<?> setTimeout(TimeoutRunnable timeoutRunnable, long timeoutMilliSec) {
		TimeoutInvokeTask timeoutInvokeTask = new TimeoutInvokeTask(timeoutRunnable);
		ScheduledFuture<?> future = this.executorService.scheduleWithFixedDelay(timeoutInvokeTask, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		timeoutInvokeTask.setFuture(future);
		timeoutRunnable.setTimeoutFuture(future);
		timeoutRunnable.setTimeoutMillisec(timeoutMilliSec);
		return future;
	}
	
	
	
	

}
