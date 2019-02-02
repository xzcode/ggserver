package com.xzcode.socket.core.starter;

import com.xzcode.socket.core.config.SocketServerConfig;

public interface SocketServerStarter {
	
	SocketServerStarter run();
	
	SocketServerStarter shutdown();
	
	void setConfig(SocketServerConfig config);
}
