package xzcode.ggserver.core.executor.timeout;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import xzcode.ggserver.core.executor.task.TimeoutRunnable;

/**
 * ggserver任务执行类型接口
 * 
 * @author zai
 * 2019-06-24 18:44:31
 */
public interface IGGServerExecution {
	/**
	 * 设置延迟计划任务
	 * 
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai 2019-02-09 18:42:03
	 */
	@Deprecated
	ScheduledFuture<?> schedule(Runnable runnable, long delayMs);

	/**
	 * 设置超时任务
	 * 
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai 2019-02-09 18:42:22
	 */
	@Deprecated
	ScheduledFuture<?> schedule(TimeoutRunnable runnable, long delayMs);
	
	
	/**
	 * 同步执行计划任务
	 * 
	 * @param syncObj
	 * @param runnable
	 * @param delayMs
	 * @return
	 * @author zai
	 * 2019-06-24 18:35:58
	 */
	@Deprecated
	ScheduledFuture<?> schedule(Object syncObj, Runnable runnable, long delayMs);

	
	/**
	 * 同步执行计划任务
	 * 
	 * @param syncObj
	 * @param runnable
	 * @param delayMs
	 * @return
	 * @author zai
	 * 2019-06-24 18:37:27
	 */
	@Deprecated
	ScheduledFuture<?> schedule(Object syncObj, TimeoutRunnable runnable, long delayMs);
	
	
	/**
	 * 执行异步任务
	 * 
	 * @param task
	 * @return
	 * @author zai
	 * 2019-04-10 17:38:38
	 */
	Future<?> asyncTask(Runnable task);

	
	/**
	 * 同步执行计划任务
	 * 
	 * @param syncObj
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-06-24 18:43:02
	 */
	ScheduledFuture<?> schedule(Object syncObj, long delayMs, TimeoutRunnable runnable);

	/**
	 * 执行计划任务
	 * 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-06-24 18:43:30
	 */
	ScheduledFuture<?> schedule(long delayMs, TimeoutRunnable runnable);

	/**
	 * 同步执行计划任务
	 * 
	 * @param syncObj
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-06-24 18:43:27
	 */
	ScheduledFuture<?> schedule(Object syncObj, long delayMs, Runnable runnable);
	
	/**
	 * 执行计划任务
	 * 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-06-24 18:43:22
	 */
	ScheduledFuture<?> schedule(long delayMs, Runnable runnable);
}
