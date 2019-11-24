package xzcode.ggserver.core.common.session;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 通道组会话实现
 * 
 * 
 * @author zai
 * 2019-11-16 23:35:14
 */
public class ChannelGroupSession implements GGSession {
	
	private GGConfig config;
	
	private String sessionId;
	
	private String host;
	
	private int port;
	
	private long expireMs;
	
	private IChannelGroup channelGroup;
	
	private Map<String, Object> attrMap = new ConcurrentHashMap<>(6);
	
	public ChannelGroupSession(GGConfig config, IChannelGroup channelGroup) {
		super();
		this.config = config;
		this.channelGroup = channelGroup;
	}

	@Override
	public void addAttribute(String key, Object value) {
		attrMap.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return attrMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String key, Class<T> t) {
		return (T) attrMap.get(key);
	}

	@Override
	public Object reomveAttribute(String key) {
		return attrMap.remove(key);
	}

	@Override
	public IGGFuture disconnect() {
		return null;

	}

	@Override
	public String getHost() {
		return this.host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int getPort() {
		return this.port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String getSessonId() {
		return this.sessionId;
	}


	@Override
	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}



	@Override
	public boolean isActive() {
		return this.channelGroup.isActive();
	}


	@Override
	public GGSession getSession() {
		return this;
	}

	@Override
	public Channel getChannel() {
		return this.channelGroup.getRandomChannel();
	}

	@Override
	public boolean isExpired() {
		return expireMs < System.currentTimeMillis();
	}


	@Override
	public void updateExpire() {
		this.expireMs = System.currentTimeMillis() + config.getSessionExpireMs();
		
	}
}
