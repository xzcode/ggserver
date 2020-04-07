package xzcode.ggserver.core.common.session.group.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.config.GGConfig;
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

}
