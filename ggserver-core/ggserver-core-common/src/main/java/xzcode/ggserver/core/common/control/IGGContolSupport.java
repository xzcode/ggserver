package xzcode.ggserver.core.common.control;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.IGGSessionSupport;

public interface IGGContolSupport extends IGGSessionSupport, IGGConfigSupport, IExecutorSupport{
	

	/**
	 * 断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	default IGGFuture disconnect() {
		return disconnect(null, 0);
	}
	
	/**
	 * 延迟断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	default IGGFuture disconnect(long delayMs) {
		return disconnect(null, delayMs);
	}

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	default IGGFuture disconnect(GGSession session) {
		return disconnect(session, 0);
	}
	
	/**
	 * 延迟断开连接
	 * 
	 * @param userId
	 * @param delayMs 延迟时间毫秒
	 * @author zai
	 * 2019-04-17 11:18:43
	 */
	default IGGFuture disconnect(GGSession session, long delayMs) {
		
		IGGFuture ggFuture = new GGFuture();
		if (session != null && session.getChannel() != null) {
			Channel channel = session.getChannel();
			if (channel != null && channel.isActive()) {
				if (delayMs <= 0) {
					ggFuture.setNettyFuture(channel.close());
				}else {
					schedule(delayMs, () -> {
						ggFuture.setNettyFuture(channel.close());
					});
				}
			}
		}
		return ggFuture;
	}
	
	
	
}
