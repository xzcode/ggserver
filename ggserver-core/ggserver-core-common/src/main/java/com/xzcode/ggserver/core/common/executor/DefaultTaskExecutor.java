package com.xzcode.ggserver.core.common.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.xzcode.ggserver.core.common.executor.task.AsyncCallableTask;
import com.xzcode.ggserver.core.common.executor.task.AsyncRunnableTask;
import com.xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import com.xzcode.ggserver.core.common.executor.timeout.TimeoutTask;
import com.xzcode.ggserver.core.common.future.GGNettyFuture;
import com.xzcode.ggserver.core.common.future.IGGFuture;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;

public class DefaultTaskExecutor implements TaskExecutor{
	
	protected EventLoopGroup executor;

	public DefaultTaskExecutor(EventLoopGroup executor) {
		this.executor = executor;
	}
	public DefaultTaskExecutor(String threadNamePrefix, int threadSize) {
		this.executor = new DefaultEventLoopGroup(threadSize, new GGThreadFactory(threadNamePrefix, false));
	}
	public DefaultTaskExecutor(int threadSize) {
		this.executor = new DefaultEventLoopGroup(threadSize, new GGThreadFactory("ITaskExecutor-", false));
	}

	@Override
	public IGGFuture submitTask(Runnable runnable) {
		return new GGNettyFuture(executor.submit(new AsyncRunnableTask(runnable)));
	}

	@Override
	public <V> IGGFuture submitTask(Callable<V> callable) {
		return new GGNettyFuture(executor.submit(new AsyncCallableTask<>(callable)));
	}
	@Override
	public IGGFuture schedule(long delay, TimeUnit timeUnit, Runnable runnable) {
		return new GGNettyFuture(executor.schedule(new AsyncRunnableTask(runnable), delay, timeUnit));
	}

	@Override
	public <V> IGGFuture schedule(long delay, TimeUnit timeUnit, Callable<V> callable) {
		return new GGNettyFuture(executor.schedule(new AsyncCallableTask<>(callable), delay, timeUnit));
	}

	@Override
	public IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
		GGNettyFuture taskFuture = new GGNettyFuture();
		afterFuture.addListener(f -> {
			AsyncRunnableTask asyncTask = new AsyncRunnableTask(runnable);
			ScheduledFuture<?> future = executor.schedule(asyncTask, delay, timeUnit);
			taskFuture.setFuture(future);
		});
		return taskFuture;
	}
	

	@Override
	public IGGFuture scheduleAfter(IGGFuture afterFuture, long delayMs, Runnable runnable) {
		return scheduleAfter(afterFuture, delayMs, TimeUnit.MILLISECONDS, runnable);
	}

	@Override
	public <V> IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Callable<V> callable) {
		GGNettyFuture taskFuture = new GGNettyFuture();
		afterFuture.addListener(f -> {
			AsyncCallableTask<V> asyncTask = new AsyncCallableTask<>(callable);
			ScheduledFuture<?> future = executor.schedule(asyncTask, delay, timeUnit);
			taskFuture.setFuture(future);
		});
		return taskFuture;
	}

	@Override
	public IGGFuture scheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
		return new GGNettyFuture(executor.scheduleWithFixedDelay(new AsyncRunnableTask(runnable), initialDelay, delay, timeUnit));
	}

	@Override
	public IGGFuture schedule(long delayMs, Runnable runnable) {
		return schedule(delayMs, TimeUnit.MILLISECONDS, runnable);
	}

	@Override
	public TaskExecutor nextEvecutor() {
		return new DefaultTaskExecutor(executor.next());
	}
	@Override
	public TimeoutTask timeoutTask(long timeoutDelay, Runnable timeoutAction) {
		return new TimeoutTask(this, timeoutDelay, timeoutAction);
	}
	@Override
	public void execute(Runnable command) {
		submitTask(command);
	}



}
