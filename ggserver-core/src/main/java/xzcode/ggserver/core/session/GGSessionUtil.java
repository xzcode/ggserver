package xzcode.ggserver.core.session;

/**
 * 会话线程工具类
 * 
 * 
 * @author zai
 * 2019-02-09 10:10:42
 */
public class GGSessionUtil {
	
	private static final ThreadLocal<GGSession> THREAD_LOCAL = new ThreadLocal<>();
	
	
	public static GGSession getSession() {
		return THREAD_LOCAL.get();
	}
	
	public static void removeSession() {
		THREAD_LOCAL.remove();
	}
	
	public static void setSession(GGSession session) {
		THREAD_LOCAL.set(session);
	}

}
