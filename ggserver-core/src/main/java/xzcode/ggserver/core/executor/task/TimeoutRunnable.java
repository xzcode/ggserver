package xzcode.ggserver.core.executor.task;

import java.util.concurrent.ScheduledFuture;

/**
 * 超时动作
 * 
 * @author zai
 * 2019-01-28 19:29:53
 */
public interface TimeoutRunnable extends Runnable {
	
	void setTimeoutFuture(ScheduledFuture<?> timeoutFuture);
	
}
