package xzcode.ggserver.core.common.executor;

import java.util.concurrent.ThreadFactory;

import io.netty.channel.DefaultEventLoop;
import xzcode.ggserver.core.common.executor.thread.SimpleThreadFactory;

/**
 * 单线程任务执行器
 * 
 * @author zai
 * 2019-12-24 17:45:30
 */
public class SingleThreadTaskExecutor extends DefaultTaskExecutor{
	
	public SingleThreadTaskExecutor() {
		super(new DefaultEventLoop());
	}

	public SingleThreadTaskExecutor(String threadNamePrefix) {
		super(new DefaultEventLoop(new SimpleThreadFactory(threadNamePrefix, false)));
	}

	public SingleThreadTaskExecutor(ThreadFactory threadFactory) {
		super(new DefaultEventLoop(threadFactory));
	}

	@Override
	public ITaskExecutor nextEvecutor() {
		return this;
	}


}
