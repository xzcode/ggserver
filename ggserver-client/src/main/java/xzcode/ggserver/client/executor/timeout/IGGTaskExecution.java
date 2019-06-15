package xzcode.ggserver.client.executor.timeout;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import xzcode.ggserver.client.executor.task.TimeoutRunnable;

public interface IGGTaskExecution {
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
	
	/**
	 * 提交任务
	 * 
	 * @param task
	 * @return
	 * @author zai
	 * 2019-04-10 17:38:38
	 */
	Future<?> submitTask(Runnable task);
}
