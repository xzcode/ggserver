package xzcode.ggserver.core.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户id与session关联
 * 
 * 
 * @author zai 2017-07-30 23:11:44
 */
public class UserSessonManager {
	
	private final ConcurrentHashMap<Object, GGSession> map = new ConcurrentHashMap<>();
	
	
	public void put(Object userId, GGSession session) {
		map.put(userId, session);
	}
	
	public GGSession get(Object userId) {
		if (userId != null) {
			return map.get(userId);
		}
		return null;
	}
	
	public GGSession remove(Object userId) {
		if (userId != null) {
			return map.remove(userId);
		}
		return null;
	}

}
