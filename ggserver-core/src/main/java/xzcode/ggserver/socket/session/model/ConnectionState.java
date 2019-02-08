package xzcode.ggserver.socket.session.model;

/**
 * 连接状态
 * 
 * 
 * @author zai
 * 2017-07-31
 */
public class ConnectionState {
	
	private boolean isLogined = false;
	
	private int heartBeatLostTimes = 0;
	
	private int maxHeartBeatLoseTimes = 3;
	
	private String host;
	
	private int port;

	public boolean isLogined() {
		return isLogined;
	}

	public void setLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public int getHeartBeatLostTimes() {
		return heartBeatLostTimes;
	}

	public void setHeartBeatLostTimes(int heartBeatLostTimes) {
		this.heartBeatLostTimes = heartBeatLostTimes;
	}

	public int getMaxHeartBeatLoseTimes() {
		return maxHeartBeatLoseTimes;
	}

	public void setMaxHeartBeatLoseTimes(int maxHeartBeatLoseTimes) {
		this.maxHeartBeatLoseTimes = maxHeartBeatLoseTimes;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
}
