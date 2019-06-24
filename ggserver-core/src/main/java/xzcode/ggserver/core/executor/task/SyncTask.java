package xzcode.ggserver.core.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

/**
 * 同步执行器
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class SyncTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SyncTask.class);
	
	private Runnable runnable;
	
	private Object syncObjc;
	
	
	public SyncTask() {
	}
	
	
	public SyncTask(Object syncObjc, Runnable runnable) {
		this.syncObjc = syncObjc;
		this.runnable = runnable;
	}

	@Override
	public void run() {
		
		try {
			if (this.syncObjc != null) {
				synchronized (syncObjc) {
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
