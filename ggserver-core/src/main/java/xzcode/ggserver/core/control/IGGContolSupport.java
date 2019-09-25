package xzcode.ggserver.core.control;

import io.netty.channel.Channel;
import xzcode.ggserver.core.config.IGGConfigSupport;
import xzcode.ggserver.core.executor.IExecutorSupport;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.IGGSessonSupport;

public interface IGGContolSupport extends IGGSessonSupport, IGGConfigSupport, IExecutorSupport{
	

	/**
	 * 断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	default void disconnect() {
		disconnect(null, 0);
	}
	
	/**
	 * 延迟断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	default void disconnect(long delayMs) {
		disconnect(null, delayMs);
	}

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	default void disconnect(Object userId) {
		disconnect(userId, 0);
	}
	
	/**
	 * 延迟断开连接
	 * 
	 * @param userId
	 * @param delayMs 延迟时间毫秒
	 * @author zai
	 * 2019-04-17 11:18:43
	 */
	default void disconnect(Object userId, long delayMs) {
		
		GGSession session = null;
		if (userId == null) {
			session = getSession();
		}else {
			session = getConfig().getUserSessonManager().get(userId);			
		}
		if (session != null && session.getChannel() != null) {
			Channel channel = session.getChannel();
			if (channel != null && channel.isActive()) {
				if (delayMs <= 0) {
					channel.close();
				}else {
					schedule(delayMs, () -> {
						channel.close();
					});
				}
			}
		}
	}
	
	
	
}
