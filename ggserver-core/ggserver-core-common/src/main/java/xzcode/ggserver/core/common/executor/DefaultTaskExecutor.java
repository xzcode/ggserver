package xzcode.ggserver.core.common.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;
import xzcode.ggserver.core.common.executor.task.AsyncCallableTask;
import xzcode.ggserver.core.common.executor.task.AsyncRunnableTask;
import xzcode.ggserver.core.common.executor.thread.SimpleThreadFactory;
import xzcode.ggserver.core.common.executor.timeout.TimeoutTask;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;

public class DefaultTaskExecutor implements ITaskExecutor{
	
	protected EventLoopGroup executor;

	public DefaultTaskExecutor(EventLoopGroup executor) {
		this.executor = executor;
	}
	public DefaultTaskExecutor(String threadNamePrefix, int threadSize) {
		this.executor = new DefaultEventLoopGroup(threadSize, new SimpleThreadFactory(threadNamePrefix, false));
	}
	public DefaultTaskExecutor(int threadSize) {
		this.executor = new DefaultEventLoopGroup(threadSize, new SimpleThreadFactory("ITaskExecutor-", false));
	}

	@Override
	public IGGFuture submitTask(Runnable runnable) {
		return new GGNettyFacadeFuture(executor.submit(new AsyncRunnableTask(runnable)));
	}

	@Override
	public <V> IGGFuture submitTask(Callable<V> callable) {
		return new GGNettyFacadeFuture(executor.submit(new AsyncCallableTask<>(callable)));
	}
	@Override
	public IGGFuture schedule(long delay, TimeUnit timeUnit, Runnable runnable) {
		return new GGNettyFacadeFuture(executor.schedule(new AsyncRunnableTask(runnable), delay, timeUnit));
	}

	@Override
	public <V> IGGFuture schedule(long delay, TimeUnit timeUnit, Callable<V> callable) {
		return new GGNettyFacadeFuture(executor.schedule(new AsyncCallableTask<>(callable), delay, timeUnit));
	}

	@Override
	public IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable) {
		GGNettyFacadeFuture taskFuture = new GGNettyFacadeFuture();
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
		GGNettyFacadeFuture taskFuture = new GGNettyFacadeFuture();
		afterFuture.addListener(f -> {
			AsyncCallableTask<V> asyncTask = new AsyncCallableTask<>(callable);
			ScheduledFuture<?> future = executor.schedule(asyncTask, delay, timeUnit);
			taskFuture.setFuture(future);
		});
		return taskFuture;
	}

	@Override
	public IGGFuture scheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
		return new GGNettyFacadeFuture(executor.scheduleWithFixedDelay(new AsyncRunnableTask(runnable), initialDelay, delay, timeUnit));
	}

	@Override
	public IGGFuture schedule(long delayMs, Runnable runnable) {
		return schedule(delayMs, TimeUnit.MILLISECONDS, runnable);
	}

	@Override
	public ITaskExecutor nextEvecutor() {
		return new DefaultTaskExecutor(executor.next());
	}
	@Override
	public TimeoutTask timeoutTask(long timeoutDelay, Runnable timeoutAction) {
		return new TimeoutTask(this, timeoutDelay, timeoutAction);
	}



}
