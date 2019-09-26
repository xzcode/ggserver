package xzcode.ggserver.core.executor;

import java.util.concurrent.TimeUnit;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;
import xzcode.ggserver.core.executor.future.GGTaskFuture;
import xzcode.ggserver.core.executor.task.GGTask;

/**
 * 任务执行器支持接口
 * 
 * @author zzz
 * 2019-09-24 15:49:41
 */
public interface IExecutorSupport {
	
	
	NioEventLoopGroup getTaskExecutor();

	/**
	 * 计划延迟任务
	 * 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zzz
	 * 2019-09-10 11:05:24
	 */
	default GGTaskFuture schedule(long delayMs, Runnable runnable) {
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
	default GGTaskFuture schedule(Object syncLock, long delayMs, Runnable runnable) {
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
	default GGTaskFuture schedule(Object syncLock, long delayMs, TimeUnit timeUnit, Runnable runnable) {
		GGTaskFuture taskFuture = new GGTaskFuture();
		GGTask syncTask = new GGTask(syncLock, runnable);
		ScheduledFuture<?> future = getTaskExecutor().schedule(syncTask, delayMs, timeUnit);
		taskFuture.setScheduledFuture(future);
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
	default GGTaskFuture scheduleAfter(GGTaskFuture afterFuture, long delay, Runnable runnable) {
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
	default GGTaskFuture scheduleAfter(GGTaskFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
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
	default GGTaskFuture scheduleAfter(GGTaskFuture afterFuture, Object syncLock, long delay, Runnable runnable) {
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
	default GGTaskFuture scheduleAfter(GGTaskFuture afterFuture, Object syncLock, long delay, TimeUnit timeUnit, Runnable runnable) {
		
		GGTaskFuture taskFuture = new GGTaskFuture();
		afterFuture.setCompleteAction(() -> {
			GGTask syncTask = new GGTask(syncLock, runnable);
			ScheduledFuture<?> future = getTaskExecutor().schedule(syncTask, delay, timeUnit);
			taskFuture.setScheduledFuture(future);
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
	default GGTaskFuture scheduleWithFixedDelay(long initialDelay, long delayMs, Runnable runnable) {
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
	default GGTaskFuture scheduleWithFixedDelay(long initialDelay, long delay, Runnable runnable, TimeUnit timeUnit) {
		GGTaskFuture taskFuture = new GGTaskFuture();
		GGTask syncTask = new GGTask(runnable);
		ScheduledFuture<?> future = getTaskExecutor().scheduleWithFixedDelay(syncTask, initialDelay, delay, timeUnit);
		taskFuture.setScheduledFuture(future);
		return taskFuture;
	}
	
	
	/**
	 * 异步任务
	 */
	default GGTaskFuture asyncTask(Runnable task) {
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
	default GGTaskFuture asyncTask(Object syncLock, Runnable task) {
		return this.schedule(syncLock, 0, task);
	}
}
