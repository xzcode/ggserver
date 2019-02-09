package xzcode.ggserver.core.executor.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * netty事件循环线程工厂
 * 
 * 
 * @author zai
 * 2018-05-29
 */
public class EventLoopGroupThreadFactory implements ThreadFactory {
	
	private String threadName;
	
	private final AtomicInteger index = new AtomicInteger(1);

	public EventLoopGroupThreadFactory(String threadName) {
		super();
		this.threadName = threadName;
	}



	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, threadName + " - " + index.getAndIncrement());
	}

}
