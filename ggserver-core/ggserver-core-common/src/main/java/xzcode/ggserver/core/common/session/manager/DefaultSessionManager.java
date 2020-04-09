package xzcode.ggserver.core.common.session.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.config.GGConfig;
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
	
	private final Map<String, GGSession> sessionMap = new ConcurrentHashMap<>(1000);
	
	public DefaultSessionManager(GGConfig config) {
		this.config = config;
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

}
