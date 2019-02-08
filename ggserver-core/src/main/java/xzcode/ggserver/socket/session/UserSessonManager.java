package xzcode.ggserver.socket.session;

import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.socket.session.imp.SocketSession;

/**
 * 用户id与session关联
 * 
 * 
 * @author zai 2017-07-30 23:11:44
 */
public class UserSessonManager {
	
	private final ConcurrentHashMap<Object, SocketSession> map = new ConcurrentHashMap<>();
	
	
	public void put(Object userId, SocketSession session) {
		map.put(userId, session);
	}
	
	public SocketSession get(Object userId) {
		if (userId != null) {
			return map.get(userId);
		}
		return null;
	}
	
	public SocketSession remove(Object userId) {
		if (userId != null) {
			return map.remove(userId);
		}
		return null;
	}

}
