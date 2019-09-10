package xzcode.ggserver.core.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.executor.future.GGTaskFuture;

/**
 * 同步执行器
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class GGTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GGTask.class);
	
	private Runnable runnable;
	
	private Object syncLock;
	
	private GGTaskFuture taskFuture;
	
	
	public GGTask() {
	}
	
	
	public GGTask(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public GGTask(Object syncLock, Runnable runnable) {
		this.syncLock = syncLock;
		this.runnable = runnable;
	}
	
	

	public GGTask(Runnable runnable, Object syncLock, GGTaskFuture taskFuture) {
		super();
		this.runnable = runnable;
		this.syncLock = syncLock;
		this.taskFuture = taskFuture;
	}
	
	public GGTask(Runnable runnable, GGTaskFuture taskFuture) {
		super();
		this.runnable = runnable;
		this.taskFuture = taskFuture;
	}

	

	@Override
	public void run() {
		
		try {
			if (this.syncLock != null) {
				synchronized (this.syncLock) {
					runnable.run();	
				}
			}else {
				runnable.run();
			}
			if (taskFuture != null) {
				taskFuture.setCompleted(true);
			}
			
		} catch (Exception e) {
			LOGGER.error("SyncTask ERROR!!", e);
		}
		
	}


	public GGTaskFuture getTaskFuture() {
		return taskFuture;
	}


	public void setTaskFuture(GGTaskFuture taskFuture) {
		this.taskFuture = taskFuture;
	}
	
	
}
