package com.xzcode.socket.core.executor;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.filter.MessageFilterManager;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.sender.SocketMessageSender;
import com.xzcode.socket.core.session.SocketSessionUtil;
import com.xzcode.socket.core.session.imp.SocketSession;

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
