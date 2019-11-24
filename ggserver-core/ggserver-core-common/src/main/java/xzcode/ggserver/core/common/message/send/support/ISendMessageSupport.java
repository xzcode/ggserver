package xzcode.ggserver.core.common.message.send.support;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.manager.ISessionManager;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai 2019-02-09 14:50:27
 */
public interface ISendMessageSupport extends ISessionSendMessageSupport, IGGConfigSupport {


	default void sendToAll(Response response) {
		try {
			GGConfig config = getConfig();
			// 发送过滤器
			if (!config.getFilterManager().doResponseFilters(response)) {
				return;
			}

			Pack pack = makePack(response);
			ISessionManager sessionManager = config.getSessionManager();
			sessionManager.eachSession(session -> {
				if (session.isActive()) {
					session.send(pack, 0L, TimeUnit.MILLISECONDS);
				}
				return true;
			});
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("GGServer sendToAll ERROR!");
		}

	}

	default IGGFuture<?> send(GGSession session, Pack pack, long delay, TimeUnit timeUnit) {
		return session.send(pack, delay, timeUnit);
	}

	default IGGFuture<?> send(GGSession session, Response response, long delay, TimeUnit timeUnit) {
		if (session != null) {
			GGConfig config = getConfig();
			// 发送过滤器
			if (!config.getFilterManager().doResponseFilters(response)) {
				return null;
			}
			try {
				if (session.isActive()) {
					session.send(makePack(response), delay, timeUnit);
				}
			} catch (Exception e) {
				GGLoggerUtil.getLogger().error("Send message Error!", e);
			}
		}
		return null;
	}
}
