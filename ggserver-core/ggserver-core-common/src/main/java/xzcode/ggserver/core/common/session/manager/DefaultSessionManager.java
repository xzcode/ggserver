package xzcode.ggserver.core.common.session.manager;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
	
	private final ConcurrentHashMap<String, GGSession> sessionMap;
	
	public DefaultSessionManager(GGConfig config) {
		super();
		this.config = config;
		 sessionMap = new ConcurrentHashMap<>(config.getWorkThreadSize() * 100);
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
		this.config.getTaskExecutor().scheduleWithFixedDelay(() -> {
			
			Iterator<Entry<String, GGSession>> it = sessionMap.entrySet().iterator();
			while (it.hasNext()) {
				GGSession session = it.next().getValue();
				if (session.isExpired()) {
					it.remove();
				}
				else if (!session.isActive()) {
					it.remove();
				}
				session.disconnect();
			}
			
		}, 100L, 100L, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public GGSession addSessionIfAbsent(GGSession session) {
		return sessionMap.putIfAbsent(session.getSessonId(), session);
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
