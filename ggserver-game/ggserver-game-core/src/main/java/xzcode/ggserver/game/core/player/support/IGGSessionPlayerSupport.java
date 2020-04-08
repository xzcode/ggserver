package xzcode.ggserver.game.core.player.support;

import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.model.IMessage;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 玩家支持接口
 * 
 * @author zzz
 * 2019-09-22 10:23:19
 */
public interface IGGSessionPlayerSupport{
	
	/**
	 * 获取session
	 * @return
	 * 
	 * @author zai
	 * 2019-10-02 23:56:40
	 */
	GGSession getSession();
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @param message
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default IGGFuture send(String actionId, Object message) {
		GGSession session = getSession();
		if (session == null) {
			return null;
		}
		return session.send(new MessageData<>(getSession(), actionId, message));
	}
	
	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 * @author zai
	 * 2019-12-25 12:04:20
	 */
	default IGGFuture send(IMessage message) {
		GGSession session = getSession();
		if (session == null) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		return session.send(new MessageData<>(getSession(), message.getActionId(), message));
	}
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default IGGFuture send(String actionId) {
		GGSession session = getSession();
		if (session != null) {
			return session.send(new MessageData<>(session, actionId, null));			
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}
	
	/**
	 * 断开连接
	 * 
	 * @author zzz
	 * 2019-09-22 10:33:22
	 */
	default IGGFuture disconnect() {
		return getSession().disconnect();
	}
	
}
