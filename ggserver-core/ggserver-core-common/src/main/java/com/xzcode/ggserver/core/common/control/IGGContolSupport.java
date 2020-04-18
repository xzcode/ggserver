package com.xzcode.ggserver.core.common.control;

import com.xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.common.session.GGSession;

public interface IGGContolSupport extends  IExecutorSupport{
	

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
	default IGGFuture  disconnect(long delayMs) {
		return disconnect(null, delayMs);
	}

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	default IGGFuture  disconnect(GGSession session) {
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
	default IGGFuture  disconnect(GGSession session, long delayMs) {
		return session.disconnect();
	}
	
	
	
}
