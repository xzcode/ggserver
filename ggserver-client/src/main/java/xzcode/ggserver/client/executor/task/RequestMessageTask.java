package xzcode.ggserver.client.executor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * socket执行任务
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class RequestMessageTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestMessageTask.class);
	
	
	
	private Runnable calback;
	
	
	public RequestMessageTask() {
	}
	
	

	public RequestMessageTask(Runnable calback) {
		super();
		this.calback = calback;
	}



	@Override
	public void run() {
		
		try {
			calback.run();
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!!", e);
		}
		
	}
	

}
