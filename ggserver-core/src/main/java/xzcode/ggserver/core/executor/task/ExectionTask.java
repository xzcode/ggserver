package xzcode.ggserver.core.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

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
	
	
	public ExectionTask() {
	}
	
	

	public ExectionTask(Runnable calback, GGSession session) {
		super();
		this.calback = calback;
		this.session = session;
	}



	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			calback.run();
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!!", e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}