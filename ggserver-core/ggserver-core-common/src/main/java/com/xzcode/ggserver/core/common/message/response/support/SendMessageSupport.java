package com.xzcode.ggserver.core.common.message.response.support;

import com.xzcode.ggserver.core.common.filter.FilterManager;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.Pack;
import com.xzcode.ggserver.core.common.message.model.IMessage;
import com.xzcode.ggserver.core.common.session.GGSession;
import com.xzcode.ggserver.core.common.session.manager.ISessionManager;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai 2019-02-09 14:50:27
 */
public interface SendMessageSupport extends IMakePackSupport {
	
	/**
	 * 获取会话管理器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:33:54
	 */
	ISessionManager getSessionManager();
	
	/**
	 * 获取过滤器管理器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:34:01
	 */
	FilterManager getFilterManager();


	/**
	 * 发送给所有会话
	 * @param response
	 * 
	 * @author zai
	 * 2019-11-27 22:09:14
	 */
	default void sendToAll(MessageData<?> response) {
		try {
			// 发送过滤器
			if (!getFilterManager().doResponseFilters(response)) {
				return;
			}

			Pack pack = makePack(response);
			ISessionManager sessionManager = getSessionManager();
			sessionManager.eachSession(session -> {
				session.send(pack);
				return true;
			});
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("GGServer sendToAll ERROR!");
		}

	}
	
	/**
	 * 发送给指定会话
	 * @param session
	 * @param pack
	 * @param delay
	 * @param timeUnit
	 * @return
	 * 
	 * @author zai
	 * 2019-11-27 22:09:31
	 */
	default IGGFuture send(GGSession session, Pack pack) {
		return session.send(pack);
	}
	
	
	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param action
	 * @param message
	 * @return
	 * @author zai
	 * 2019-11-29 15:24:23
	 */
	default IGGFuture send(GGSession session, String action, Object message) {
		return send(new MessageData<>(session, action, message));
	}
	
	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param action
	 * @param message
	 * @param delay
	 * @param timeUnit
	 * @return
	 * @author zai
	 * 2019-11-29 15:23:47
	 */
	default IGGFuture send(GGSession session, IMessage message) {
		return send(new MessageData<>(session, message.getActionId(), message));
	}

	/**
	 * 发送消息
	 * @param session
	 * @param response
	 * @param delay
	 * @param timeUnit
	 * @return
	 * 
	 * @author zai
	 * 2019-11-27 21:53:08
	 */
	default IGGFuture send(MessageData<?> messageData) {
		GGSession session = messageData.getSession();
		if (session != null) {
			try {
			// 发送过滤器
			if (!getFilterManager().doResponseFilters(messageData)) {
				return null;
			}
				session.send(makePack(messageData));
			} catch (Exception e) {
				GGLoggerUtil.getLogger().error("Send message Error!", e);
			}
		}
		return null;
	}

	/**
	 * 发送包
	 * 
	 * @param pack
	 * @return
	 * @author zai
	 * 2019-12-16 10:20:47
	 */
	default IGGFuture send(Pack pack) {
		return send(null, pack);
	}
	
	
}
