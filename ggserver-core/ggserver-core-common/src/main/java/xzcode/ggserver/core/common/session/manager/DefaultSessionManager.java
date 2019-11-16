package xzcode.ggserver.core.common.session.manager;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * session管理
 * 
 * 
 * @author zai
 * 2019-10-02 23:17:21
 */
public class DefaultSessionManager implements ISessionManager {
	
	private final ConcurrentHashMap<String, GGSession> sessionMap = new ConcurrentHashMap<>(128);
	
	
	@Override
	public void addSession(GGSession session) {
		sessionMap.put(session.getSessonId(), session);
	}
	
	@Override
	public GGSession getSession(String sessionId) {
		if (sessionId != null) {
			return sessionMap.get(sessionId);
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

}
