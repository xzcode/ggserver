package xzcode.ggserver.core.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionUtil;

/**
 * socket执行任务
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class ExectionTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ExectionTask.class);
	
	private GGSession session;
	
	private Runnable calback;
	
	private Object syncObjc;
	
	
	public ExectionTask() {
	}
	
	

	public ExectionTask(Runnable calback, GGSession session) {
		this.calback = calback;
		this.session = session;
	}
	
	public ExectionTask(Object syncObjc, Runnable calback, GGSession session) {
		this.syncObjc = syncObjc;
		this.calback = calback;
		this.session = session;
	}



	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		try {
			if (this.syncObjc != null) {
				synchronized (syncObjc) {
					calback.run();					
				}
			}else {
				calback.run();
			}
			
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!!", e);
		}
		GGSessionUtil.removeSession();
		
	}
	

}
