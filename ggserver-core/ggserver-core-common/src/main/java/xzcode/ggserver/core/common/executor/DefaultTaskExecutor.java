package xzcode.ggserver.core.common.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.DefaultEventLoopGroup;
<<<<<<< HEAD:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultEventLoopGroupTaskExecutor.java
=======
import io.netty.channel.EventLoopGroup;
>>>>>>> remotes/origin/0.1.0-SNAPSHOT:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultTaskExecutor.java
import io.netty.util.concurrent.ScheduledFuture;
import xzcode.ggserver.core.common.executor.task.AsyncCallableTask;
import xzcode.ggserver.core.common.executor.task.AsyncRunnableTask;
import xzcode.ggserver.core.common.executor.thread.SimpleThreadFactory;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;

<<<<<<< HEAD:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultEventLoopGroupTaskExecutor.java
public class DefaultEventLoopGroupTaskExecutor implements ITaskExecutor{
	
	private DefaultEventLoopGroup executor;

	public DefaultEventLoopGroupTaskExecutor(DefaultEventLoopGroup executor) {
=======
public class DefaultTaskExecutor implements ITaskExecutor{
	
	protected EventLoopGroup executor;

	public DefaultTaskExecutor(EventLoopGroup executor) {
>>>>>>> remotes/origin/0.1.0-SNAPSHOT:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultTaskExecutor.java
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
<<<<<<< HEAD:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultEventLoopGroupTaskExecutor.java
	public ExecutorService nextEvecutor() {
		return executor.next();
	}

=======
	public ITaskExecutor nextEvecutor() {
		return new DefaultTaskExecutor(executor.next());
	}


>>>>>>> remotes/origin/0.1.0-SNAPSHOT:ggserver-core/ggserver-core-common/src/main/java/xzcode/ggserver/core/common/executor/DefaultTaskExecutor.java

}
