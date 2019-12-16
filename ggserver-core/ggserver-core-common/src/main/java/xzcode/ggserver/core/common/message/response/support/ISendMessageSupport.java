package xzcode.ggserver.core.common.message.response.support;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.manager.ISessionManager;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai 2019-02-09 14:50:27
 */
public interface ISendMessageSupport extends IMakePackSupport {
	
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
	IFilterManager getFilterManager();


	/**
	 * 发送给所有会话
	 * @param response
	 * 
	 * @author zai
	 * 2019-11-27 22:09:14
	 */
	default void sendToAll(Response response) {
		try {
			// 发送过滤器
			if (!getFilterManager().doResponseFilters(response)) {
				return;
			}

			Pack pack = makePack(response);
			ISessionManager sessionManager = getSessionManager();
			sessionManager.eachSession(session -> {
				session.send(pack, 0L, TimeUnit.MILLISECONDS);
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
	default IGGFuture send(GGSession session, Pack pack, long delay, TimeUnit timeUnit) {
		return session.send(pack, delay, timeUnit);
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
		return send(new Response(session, null, action, message), 0L, TimeUnit.MILLISECONDS);
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
	default IGGFuture send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		return send(new Response(session, null, action, message), delay, timeUnit);
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
	default IGGFuture send(Response response, long delay, TimeUnit timeUnit) {
		GGSession session = response.getSession();
		if (session != null) {
			try {
			// 发送过滤器
				if (!getFilterManager().doResponseFilters(response)) {
					return null;
				}
				session.send(makePack(response), delay, timeUnit);
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
		return send(null, pack, 0L, TimeUnit.MILLISECONDS);
	}
	
	
}
