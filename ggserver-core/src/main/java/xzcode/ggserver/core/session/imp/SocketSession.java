package xzcode.ggserver.core.session.imp;

import io.netty.channel.Channel;
import xzcode.ggserver.core.session.GGSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认session实现
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketSession implements GGSession {
	
	private Object registeredUserId;
	
	private Map<Object, Object> attrMap;// = new ConcurrentHashMap<>();
	
	private Channel channel;
	
	private String host;
	
	private int port;
	
	private boolean isActive = true;
	
	public SocketSession() {}
	
	public void inActive() {
		if (isActive == true) {
			this.isActive = false;
			this.channel = null;
		}
	}
	





	public SocketSession(Channel channel, String host, int port) {
		super();
		this.channel = channel;
		this.host = host;
		this.port = port;
	}



	


	@Override
	public String toString() {
		return "SocketSession [registeredUserId=" + registeredUserId + ", attrMap=" + attrMap + ", channel=" + channel
				+ ", host=" + host + ", port=" + port + ", isActive=" + isActive + "]";
	}

	public SocketSession(Channel channel) {
		super();
		this.channel = channel;
	}
	
	@Override
	public void register(Object registeredUserId) {
		this.registeredUserId = registeredUserId;
	}
	
	@Override
	public void unregister() {
		this.registeredUserId = null;
		this.attrMap = null;
	}

	public Map<Object, Object> getAttrMapMap(){
		if (this.attrMap == null) {
			synchronized (this) {
				if (this.attrMap == null) {
					this.attrMap = new ConcurrentHashMap<>();
				}
			}
		}
		return this.attrMap;
	}
	
	@Override
	public void addAttribute(Object key, Object value) {
		getAttrMapMap().put(key, value);

	}

	@Override
	public Object getAttribute(Object key) {
		return getAttrMapMap().get(key);
	}

	@Override
	public Object reomveAttribute(Object key) {
		return getAttrMapMap().remove(key);
	}

	public void clear() {
		attrMap.clear();
	}

	@Override
	public void disconnect() {
		this.channel.close();

	}
	
	@Override
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	@Override
	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	@Override
	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public Object getRegisteredUserId() {
		return registeredUserId;
	}
	
	@Override
	public void setRegisteredUserId(Object registeredUserId) {
		this.registeredUserId = registeredUserId;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		if (isActive == false) {
			this.isActive = isActive;			
		}
	}
	
	
	
	

}
