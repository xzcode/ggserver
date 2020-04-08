package xzcode.ggserver.core.common.session.group.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.model.IMessage;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.group.GGSessionGroup;
import xzcode.ggserver.core.common.session.group.impl.DefaultSessionGroup;

/**
 * 会话组管理器
 *
 * @author zai
 * 2020-04-07 15:11:36
 */
public class GGSessionGroupManager {
	
	/**
	 * 公共配置
	 */
	private GGConfig config;
	/**
	 * 会话组集合
	 */
	protected Map<String, GGSessionGroup> sessionGroupMap = new ConcurrentHashMap<String, GGSessionGroup>();
	
	/**
	 * 默认组
	 */
	protected GGSessionGroup defaultGroup;
	
	public GGSessionGroupManager(GGConfig config) {
		super();
		this.config = config;
	}

	/**
	 * 添加会话
	 *
	 * @param sessionGroupId 组id
	 * @param session 会话
	 * @author zai
	 * 2020-04-07 15:11:47
	 */
	public void addSession(String sessionGroupId, GGSession session) {
		if (session == null) {
			return;
		}
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(sessionGroupId);
		if (sessionGroup == null) {
			sessionGroup = new DefaultSessionGroup(sessionGroupId, this.config);
			GGSessionGroup putIfAbsent = sessionGroupMap.putIfAbsent(sessionGroupId, sessionGroup);
			if (putIfAbsent != null) {
				sessionGroup = putIfAbsent;
			}
		}
		sessionGroup.addSession(session);
	}

	/**
	 * 移除会话
	 *
	 * @param sessionGroupId
	 * @param session
	 * @author zai
	 * 2020-04-07 15:12:00
	 */
	public void removeSession(String sessionGroupId, GGSession session) {
		if (session == null) {
			return;
		}
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(sessionGroupId);
		if (sessionGroup != null) {
			sessionGroup.removeSession(session);
		}
	}
	
	/**
	 * 发送给指定组内的所有session
	 *
	 * @param groupId
	 * @param pack
	 * @return
	 * @author zai
	 * 2020-04-08 16:23:40
	 */
	public IGGFuture sendToAll(String groupId, Pack pack) {
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(groupId);
		if (sessionGroup != null) {
			return sessionGroup.sendToAll(pack);
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}
	
	/**
	 * 发送到默认组内的随机一个session
	 *
	 * @param message
	 * @return
	 * @author zai
	 * 2020-04-08 16:21:47
	 */
	public IGGFuture sendToDefaultGroupRandomOne(IMessage message) {
		if (this.defaultGroup != null) {
			return this.defaultGroup.sendToRandomOne(message);
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}

	/**
	 * 随机发送到一个会话中
	 *
	 * @param pack
	 * @return
	 * @author zai 2020-04-07 14:49:06
	 */
	public IGGFuture sendToRandomOne(String groupId, Pack pack) {
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(groupId);
		if (sessionGroup != null) {
			return sessionGroup.sendToRandomOne(pack);
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}
	
	/**
	 * 随机发送到一个会话中
	 *
	 * @param message
	 * @return
	 * @author zai
	 * 2020-04-07 15:38:44
	 */
	public IGGFuture sendToRandomOne(String groupId, IMessage message) {
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(groupId);
		if (sessionGroup != null) {
			return sessionGroup.sendToRandomOne(message);
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}
	
	/**
	 * 设置默认组
	 *
	 * @param sessionGroupId
	 * @author zai
	 * 2020-04-08 16:18:20
	 */
	public void setDefaultGroup(String sessionGroupId) {
		GGSessionGroup group = this.sessionGroupMap.get(sessionGroupId);
		if (group != null) {
			this.defaultGroup = group;
		}
	}
	
	public GGSessionGroup getDefaultGroup() {
		return defaultGroup;
	}
	

}
