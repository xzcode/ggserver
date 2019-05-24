package xzcode.ggserver.core.executor.task;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeoutInvokeTask  implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeoutInvokeTask.class);
	
	private Runnable timeoutRunnable;
	private ScheduledFuture<?> future;
	
	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}
	
	public TimeoutInvokeTask(Runnable timeoutRunnable) {
		this.timeoutRunnable = timeoutRunnable;
	}
	@Override
	public void run() {
		try {
			
			timeoutRunnable.run();
			if (timeoutRunnable instanceof TimeoutRunnable) {
				((TimeoutRunnable) timeoutRunnable).setTimeoutFuture(null);
			}
		} catch (Exception e) {
			logger.error("倒计时任务异常：",e);
		}
		this.future.cancel(true);
	}
}
