package xzcode.ggserver.socket.executor;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.socket.filter.MessageFilterManager;
import xzcode.ggserver.socket.sender.SendModel;
import xzcode.ggserver.socket.sender.SocketMessageSender;
import xzcode.ggserver.socket.session.SocketSessionUtil;
import xzcode.ggserver.socket.session.imp.SocketSession;

/**
 * socket执行任务
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class SocketRunnableTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SocketRunnableTask.class);
	
	
	/**
	 * socket消息体对象
	 */
	private SocketSession session;
	
	private Runnable calback;
	
	
	public SocketRunnableTask() {
	}
	
	

	public SocketRunnableTask(Runnable calback,SocketSession session) {
		super();
		this.calback = calback;
		this.session = session;
	}



	@Override
	public void run() {
		
		SocketSessionUtil.setSession(this.session);
		try {
			calback.run();
		} catch (Exception e) {
			LOGGER.error("Socket Callable Task ERROR!!", e);
		}
		SocketSessionUtil.removeSession();
		
	}
	

}
