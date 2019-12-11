package xzcode.ggserver.core.common.executor.support;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 计划任务执行支持接口
 * 
 * 
 * @author zai
 * 2019-12-01 16:15:52
 */
public interface IExecutorSupport extends ITaskExecutor {
	
	ITaskExecutor getTaskExecutor();

	@Override
	default IGGFuture<?> submitTask(Runnable runnable) {
		return getTaskExecutor().submitTask(runnable);
	}

	@Override
	default <V> IGGFuture<V> submitTask(Callable<V> callable) {
		return getTaskExecutor().submitTask(callable);
	}

	@Override
	default IGGFuture<?> schedule(long delay, TimeUnit timeUnit, Runnable runnable) {
		return  getTaskExecutor().schedule(delay, timeUnit, runnable);
	}

	@Override
	default <V> IGGFuture<V> schedule(long delay, TimeUnit timeUnit, Callable<V> callable) {
		return  getTaskExecutor().schedule(delay, timeUnit, callable);
	}

	@Override
	default IGGFuture<?> scheduleAfter(IGGFuture<?> afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
		return  getTaskExecutor().scheduleAfter(afterFuture, delay, timeUnit, runnable);
	}

	@Override
	default <V> IGGFuture<V> scheduleAfter(IGGFuture<?> afterFuture, long delay, TimeUnit timeUnit,
			Callable<V> callable) {
		return  getTaskExecutor().scheduleAfter(afterFuture, delay, timeUnit, callable);
	}

	@Override
	default IGGFuture<?> scheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
		return getTaskExecutor().scheduleWithFixedDelay(initialDelay, delay, timeUnit, runnable);
	}
	
	
	
}
