package com.xzcode.ggserver.core.common.session.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.executor.TaskExecutor;
import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * session管理器
 * 
 * 
 * @author zai
 * 2019-10-02 23:17:21
 */
public class DefaultSessionManager implements ISessionManager {
	
	private GGConfig config;
	
	private final Map<String, GGSession> sessionMap = new ConcurrentHashMap<>(1000);
	
	public DefaultSessionManager(GGConfig config) {
		this.config = config;
		
		this.startSessionExpireCheckTask();
	}
	
	
	/**
	 * 启动检查session过期任务
	 *
	 * @author zai
	 * 2020-04-13 10:25:24
	 */
	protected void startSessionExpireCheckTask() {
		TaskExecutor taskExecutor = this.config.getTaskExecutor();
		taskExecutor.schedule(10 * 1000L, () -> {
			for (Entry<String, GGSession> entry : sessionMap.entrySet()) {
				GGSession session = entry.getValue();
				session.checkExpire();
				if (session.isExpired()) {
					session.disconnect();
				}
			}
		});
	}
	
	
	@Override
	public GGSession addSessionIfAbsent(GGSession session) {
		GGSession putIfAbsent = sessionMap.putIfAbsent(session.getSessonId(), session);
		if (putIfAbsent == null) {
			//添加断开监听
			session.addDisconnectListener( s -> {
				//断开连接从管理器中移除session
				remove(s.getSessonId());
				
			});
		}
		return session;
	}
	
	@Override
	public GGSession getSession(String sessionId) {
		if (sessionId != null) {
			GGSession session = sessionMap.get(sessionId);
			if (session != null) {
				session.updateExpire();
			}
			return session;
		}
		return null;
	}
	
	@Override
	public GGSession remove(String sessionId) {
		if (sessionId != null) {
			return sessionMap.remove(sessionId);
		}
		return null;
	}

	@Override
	public void eachSession(IEachData<GGSession> eachData) {
		
		for (Entry<String, GGSession> entry : sessionMap.entrySet()) {
			if (!eachData.each(entry.getValue())) {
				break;
			}
		}
	}

	@Override
	public void disconnectAllSession() {
		if (sessionMap != null) {
			eachSession(session -> {
				session.disconnect();
				return true;
			});
		}
	}


	@Override
	public GGSession randomGetSession() {
		Set<Entry<String, GGSession>> entrySet = sessionMap.entrySet();
		if (entrySet.size() == 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Entry<String, GGSession> entry = (Entry<String, GGSession>) entrySet.toArray()[ThreadLocalRandom.current().nextInt(entrySet.size())];
		return entry.getValue();
	}

}
