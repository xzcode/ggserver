package xzcode.ggserver.client.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.executor.task.TimeoutInvokeTask;
import xzcode.ggserver.client.executor.task.TimeoutRunnable;
import xzcode.ggserver.client.executor.timeout.IGGTaskExecution;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务线程执行器
 * 
 * 
 * @author zai
 * 2018-05-29
 */
public class GGTaskExecutor extends ScheduledThreadPoolExecutor implements IGGTaskExecution{
	
	private static final Logger logger = LoggerFactory.getLogger(GGTaskExecutor.class);
	
	private final static AtomicInteger threadIndex = new AtomicInteger(1);
	
	private GGClientConfig config;
	
	public GGTaskExecutor(GGClientConfig config) {
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
			return new Thread(r,"GGServer Task Thread - " + threadIndex.getAndIncrement());
		});
		
		this.setKeepAliveTime(this.config.getReqTaskKeepAliveTime(), TimeUnit.MILLISECONDS);
		
		
	}
	
	@Override
	public ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec) {
		TimeoutInvokeTask task = new TimeoutInvokeTask(runnable);
		ScheduledFuture<?> future = this.scheduleWithFixedDelay(task, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		task.setFuture(future);
		return future;
	}
	@Override
	public ScheduledFuture<?> setTimeout(TimeoutRunnable runnable, long timeoutMilliSec) {
		TimeoutInvokeTask task = new TimeoutInvokeTask(runnable);
		ScheduledFuture<?> future = this.scheduleWithFixedDelay(task, timeoutMilliSec, timeoutMilliSec , TimeUnit.MILLISECONDS);
		task.setFuture(future);
		runnable.setTimeoutFuture(future);
		return future;
	}

	@Override
	public Future<?> submitTask(Runnable task) {
		return submit(task);
	}

}
