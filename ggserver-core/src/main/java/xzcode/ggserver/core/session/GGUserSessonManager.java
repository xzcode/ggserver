package xzcode.ggserver.core.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户id与session关联
 * 
 * 
 * @author zai 2017-07-30 23:11:44
 */
public class GGUserSessonManager {
	
	private final ConcurrentHashMap<Object, GGSession> sessionMap = new ConcurrentHashMap<>();
	
	
	public void put(Object userId, GGSession session) {
		sessionMap.put(userId, session);
	}
	
	public GGSession get(Object userId) {
		if (userId != null) {
			return sessionMap.get(userId);
		}
		return null;
	}
	
	public GGSession remove(Object userId) {
		if (userId != null) {
			return sessionMap.remove(userId);
		}
		return null;
	}
	
	public ConcurrentHashMap<Object, GGSession> getSessionMap() {
		return sessionMap;
	}

}
