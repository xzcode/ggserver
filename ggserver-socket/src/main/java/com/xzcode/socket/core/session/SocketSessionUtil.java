package com.xzcode.socket.core.session;

import com.xzcode.socket.core.session.imp.SocketSession;

public class SocketSessionUtil {
	
	private static final ThreadLocal<SocketSession> THREAD_LOCAL = new ThreadLocal<>();
	
	
	public static SocketSession getSession() {
		return THREAD_LOCAL.get();
	}
	
	public static void removeSession() {
		THREAD_LOCAL.remove();
	}
	
	public static void setSession(SocketSession session) {
		THREAD_LOCAL.set(session);
	}

}
