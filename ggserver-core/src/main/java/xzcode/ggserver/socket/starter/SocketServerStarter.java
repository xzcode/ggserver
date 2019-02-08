package xzcode.ggserver.socket.starter;

import xzcode.ggserver.socket.config.GGSocketConfig;

public interface SocketServerStarter {
	
	SocketServerStarter run();
	
	SocketServerStarter shutdown();
	
	void setConfig(GGSocketConfig config);
}
