package xzcode.ggserver.core.common.session.manager;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * session管理器
 * 
 * 
 * @author zai
 * 2019-10-02 23:17:21
 */
public class DefaultSessionManager implements ISessionManager {
	
	private GGConfig config;
	
	private final ConcurrentHashMap<String, GGSession> sessionMap;
	
	public DefaultSessionManager(GGConfig config) {
		this.config = config;
		int initSize = config.getWorkThreadSize() * 1000;
		sessionMap = new ConcurrentHashMap<>(initSize);
		this.startSessionExpireTask();
	}
	
	/**
	 * 启动会话超时检查任务
	 * 
	 * 
	 * @author zai
	 * 2019-11-17 00:40:04
	 */
	private void startSessionExpireTask() {
		this.config.getTaskExecutor().scheduleWithFixedDelay( 1000L, 100L, TimeUnit.MILLISECONDS,() -> {
			
			Iterator<Entry<String, GGSession>> it = sessionMap.entrySet().iterator();
			while (it.hasNext()) {
				GGSession session = it.next().getValue();
				if (session.isExpired()) {
					config.getEventManager().emitEvent(new EventData<Void>(session, GGEvents.Session.EXPIRED, null));
					it.remove();
				}
			}
		});
	}
	
	@Override
	public GGSession addSessionIfAbsent(GGSession session) {
		return sessionMap.putIfAbsent(session.getSessonId(), session);
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
	public void clearAllSession() {
		if (sessionMap != null) {
			eachSession(session -> {
				session.expire();
				return true;
			});
			sessionMap.clear();
		}
	}

}
