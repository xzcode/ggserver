package xzcode.ggserver.core.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * session管理
 * 
 * 
 * @author zai
 * 2019-10-02 23:17:21
 */
public class GGSessionManager {
	
	private final ConcurrentHashMap<Object, GGSession> sessionMap = new ConcurrentHashMap<>(128);
	
	
	public void put(Object sessionId, GGSession session) {
		sessionMap.put(sessionId, session);
	}
	
	public GGSession get(Object sessionId) {
		if (sessionId != null) {
			return sessionMap.get(sessionId);
		}
		return null;
	}
	
	public GGSession remove(Object sessionId) {
		if (sessionId != null) {
			return sessionMap.remove(sessionId);
		}
		return null;
	}
	
	public ConcurrentHashMap<Object, GGSession> getSessionMap() {
		return sessionMap;
	}

}
