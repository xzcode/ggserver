package com.xzcode.ggcloud.session.group.common.group.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.ggcloud.session.group.common.group.GGSessionGroup;
import com.xzcode.ggcloud.session.group.common.group.impl.DefaultSessionGroup;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.future.GGFailedFuture;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.common.message.Pack;
import com.xzcode.ggserver.core.common.message.model.IMessage;
import com.xzcode.ggserver.core.common.session.GGSession;

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
		
		GGSessionGroup sessionGroup = this.sessionGroupMap.get(sessionGroupId);
		if (sessionGroup == null) {
			sessionGroup = new DefaultSessionGroup(sessionGroupId, this.config);
			GGSessionGroup putIfAbsent = sessionGroupMap.putIfAbsent(sessionGroupId, sessionGroup);
			if (putIfAbsent != null) {
				sessionGroup = putIfAbsent;
			}
		}
		if (session == null) {
			return;
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

}
