package xzcode.ggserver.core.common.executor;

import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.ScheduledFuture;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.executor.task.GGTask;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 任务执行器支持接口
 * 
 * @author zzz
 * 2019-09-24 15:49:41
 */
public interface IExecutorSupport extends IGGConfigSupport{
	
	

	/**
	 * 计划延迟任务
	 * 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:05:24
	 */
	default IGGFuture schedule(long delayMs, Runnable runnable) {
		return schedule(null, delayMs, TimeUnit.MILLISECONDS, runnable);
	}
	
	
	/**
	 * 计划延迟任务
	 * 
	 * @param syncLock 同步对象
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:05:58
	 */
	default IGGFuture schedule(Object syncLock, long delayMs, Runnable runnable) {
		return schedule(syncLock, delayMs, TimeUnit.MILLISECONDS, runnable);
	}
	
	/**
	 * 计划延迟任务
	 * 
	 * @param syncLock 同步对象
	 * @param delayMs
	 * @param timeUnit
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:06:09
	 */
	default IGGFuture schedule(Object syncLock, long delayMs, TimeUnit timeUnit, Runnable runnable) {
		IGGFuture taskFuture = new GGFuture();
		GGTask syncTask = new GGTask(syncLock, runnable);
		ScheduledFuture<?> future = getConfig().getTaskExecutor().schedule(syncTask, delayMs, timeUnit);
		taskFuture.setNettyFuture(future);
		return taskFuture;
	}
	
	/**
	 * 跟随上个任务执行完毕后执行下一个任务
	 * 
	 * @param afterFuture 跟随的任务future
	 * @param delay
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-11 11:49:50
	 */
	default IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, Runnable runnable) {
		return scheduleAfter(afterFuture, null, delay, TimeUnit.MILLISECONDS, runnable);
	}
	
	/**
	 * 跟随上个任务执行完毕后执行下一个任务
	 * 
	 * @param afterFuture 跟随的任务future
	 * @param delay
	 * @param timeUnit
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-11 11:51:13
	 */
	default IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
		return scheduleAfter(afterFuture, null, delay, timeUnit, runnable);
	}
	
	/**
	 * 跟随上个任务执行完毕后执行下一个任务
	 * 
	 * @param afterFuture 跟随的任务future
	 * @param syncLock
	 * @param delay
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-11 11:54:30
	 */
	default IGGFuture scheduleAfter(IGGFuture afterFuture, Object syncLock, long delay, Runnable runnable) {
		return scheduleAfter(afterFuture, syncLock, delay, TimeUnit.MILLISECONDS, runnable);
	}
	
	/**
	 * 跟随上个任务执行完毕后执行下一个任务
	 * 
	 * @param afterFuture 跟随的任务future
	 * @param syncLock
	 * @param delay
	 * @param timeUnit
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-11 11:51:19
	 */
	default IGGFuture scheduleAfter(IGGFuture afterFuture, Object syncLock, long delay, TimeUnit timeUnit, Runnable runnable) {
		
		IGGFuture taskFuture = new GGFuture();
		afterFuture.onComplete(() -> {
			GGTask syncTask = new GGTask(syncLock, runnable);
			ScheduledFuture<?> future = getConfig().getTaskExecutor().schedule(syncTask, delay, timeUnit);
			taskFuture.setNettyFuture(future);
		});
		return taskFuture;
	}
	
	/**
	 * 计划循环任务
	 * 
	 * @param initialDelay 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:06:38
	 */
	default IGGFuture scheduleWithFixedDelay(long initialDelay, long delayMs, Runnable runnable) {
		return scheduleWithFixedDelay(initialDelay, delayMs, runnable, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 计划延迟任务
	 * 
	 * @param initialDelay
	 * @param delayMs
	 * @param timeUnit
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:06:09
	 */
	default IGGFuture scheduleWithFixedDelay(long initialDelay, long delay, Runnable runnable, TimeUnit timeUnit) {
		IGGFuture taskFuture = new GGFuture();
		GGTask syncTask = new GGTask(runnable);
		ScheduledFuture<?> future = getConfig().getTaskExecutor().scheduleWithFixedDelay(syncTask, initialDelay, delay, timeUnit);
		taskFuture.setNettyFuture(future);
		return taskFuture;
	}
	
	
	/**
	 * 异步任务
	 */
	default IGGFuture asyncTask(Runnable task) {
		return this.schedule(0, task);
	}
	
	/**
	 * 异步任务
	 * 
	 * @param syncLock
	 * @param task
	 * @return
	 * @author zai
	 * 2019-07-08 11:56:08
	 */
	default IGGFuture asyncTask(Object syncLock, Runnable task) {
		return this.schedule(syncLock, 0, task);
	}
}
