package com.xzcode.ggcloud.session.group.common.group.impl;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.ggcloud.session.group.common.group.GGSessionGroup;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * 抽象会话组
 *
 * @author zai 2020-04-07 14:12:29
 */
public class DefaultSessionGroup implements GGSessionGroup {

	/**
	 * 公共配置
	 */
	protected GGConfig config;
	
	/**
	 * 会话组id
	 */
	protected String groupId;
	
	/**
	 * 会话集合
	 */
	protected Map<String, GGSession> sessionMap = new ConcurrentHashMap<String, GGSession>();
	
	
	
	public DefaultSessionGroup(String groupId, GGConfig config) {
		this.groupId = groupId;
		this.config = config;
	}

	@Override
	public Map<String, GGSession> getSessionMap() {
		return this.sessionMap;
	}

	public void addSession(GGSession session) {
		if (session == null) {
			return;
		}
		this.sessionMap.put(session.getSessonId(), session);
		session.addDisconnectListener(se -> {
			removeSession(se);
		});
	}

	public void removeSession(GGSession session) {
		if (session == null) {
			return;
		}
		this.sessionMap.remove(session.getSessonId());
	}

	@Override
	public Charset getCharset() {
		return this.config.getCharset();
	}

	@Override
	public ISerializer getSerializer() {
		return this.config.getSerializer();
	}

	@Override
	public String getGroupId() {
		return this.groupId;
	}

}
