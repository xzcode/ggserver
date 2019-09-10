package xzcode.ggserver.core.executor.timeout;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * ggserver任务执行类型接口
 * 
 * @author zai
 * 2019-06-24 18:44:31
 */
public interface IGGServerExecution {
	
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
