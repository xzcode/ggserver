package xzcode.ggserver.core.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	
	public GGTask() {
	}
	
	
	public GGTask(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public GGTask(Object syncLock, Runnable runnable) {
		this.syncLock = syncLock;
		this.runnable = runnable;
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
			
		} catch (Exception e) {
			LOGGER.error("SyncTask ERROR!!", e);
		}
		
	}
}
