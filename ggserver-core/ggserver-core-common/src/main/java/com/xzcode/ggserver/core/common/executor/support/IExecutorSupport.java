package com.xzcode.ggserver.core.common.executor.support;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.xzcode.ggserver.core.common.executor.TaskExecutor;
import com.xzcode.ggserver.core.common.executor.timeout.TimeoutTask;
import com.xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 计划任务执行支持接口
 * 
 * 
 * @author zai
 * 2019-12-01 16:15:52
 */
public interface IExecutorSupport extends TaskExecutor {
	
	/**
	 * 获取任务执行器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-21 11:16:45
	 */
	TaskExecutor getTaskExecutor();

	@Override
	default IGGFuture submitTask(Runnable runnable) {
		return getTaskExecutor().submitTask(runnable);
	}

	@Override
	default <V> IGGFuture submitTask(Callable<V> callable) {
		return getTaskExecutor().submitTask(callable);
	}

	@Override
	default IGGFuture schedule(long delay, TimeUnit timeUnit, Runnable runnable) {
		return  getTaskExecutor().schedule(delay, timeUnit, runnable);
	}

	@Override
	default <V> IGGFuture schedule(long delay, TimeUnit timeUnit, Callable<V> callable) {
		return  getTaskExecutor().schedule(delay, timeUnit, callable);
	}

	@Override
	default IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
		return  getTaskExecutor().scheduleAfter(afterFuture, delay, timeUnit, runnable);
	}
	
	@Override
	default IGGFuture scheduleAfter(IGGFuture afterFuture, long delayMs, Runnable runnable) {
		return  getTaskExecutor().scheduleAfter(afterFuture, delayMs, TimeUnit.MILLISECONDS, runnable);
	}

	@Override
	default <V> IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit,
			Callable<V> callable) {
		return  getTaskExecutor().scheduleAfter(afterFuture, delay, timeUnit, callable);
	}

	@Override
	default IGGFuture scheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
		return getTaskExecutor().scheduleWithFixedDelay(initialDelay, delay, timeUnit, runnable);
	}

	@Override
	default IGGFuture schedule(long delayMs, Runnable runnable) {
		return getTaskExecutor().schedule(delayMs, runnable);
	}

	@Override
	default TimeoutTask timeoutTask(long timeoutDelay, Runnable timeoutAction) {
		return getTaskExecutor().timeoutTask(timeoutDelay, timeoutAction);
	}

	@Override
	default TaskExecutor nextEvecutor() {
		return getTaskExecutor().nextEvecutor();
	}
	
	@Override
	default void execute(Runnable command) {
		getTaskExecutor().submitTask(command);
	}
	
}
