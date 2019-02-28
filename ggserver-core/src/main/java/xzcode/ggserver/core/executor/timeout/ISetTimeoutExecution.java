package xzcode.ggserver.core.executor.timeout;

import java.util.concurrent.ScheduledFuture;

import xzcode.ggserver.core.executor.task.TimeoutRunnable;

public interface ISetTimeoutExecution {
	/**
	 * 设置超时任务
	 * 
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai 2019-02-09 18:42:03
	 */
	ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec);

	/**
	 * 设置超时任务
	 * 
	 * @param runnable
	 * @param timeoutMilliSec
	 * @return
	 * 
	 * @author zai 2019-02-09 18:42:22
	 */
	ScheduledFuture<?> setTimeout(TimeoutRunnable runnable, long timeoutMilliSec);
}