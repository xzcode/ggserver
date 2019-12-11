package xzcode.ggserver.core.common.session;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.future.GGSuccessFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.meta.IMetadata;

/**
 * 通道组会话实现
 * 
 * 
 * @author zai
 * 2019-11-16 23:35:14
 */
public class ChannelGroupSession<C extends GGConfig> implements GGSession, IGGConfigSupport<C> {
	
	private C config;
	
	private String sessionId;
	
	private String host;
	
	private int port;
	
	private long expireMs;
	
	private IChannelGroup channelGroup;
	
	private IMetadata metadata;
	
	private Map<String, Object> attrMap = new ConcurrentHashMap<>(6);
	
	public ChannelGroupSession(C config, IChannelGroup channelGroup) {
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
	public IGGFuture<?> disconnect() {
		config.getSessionManager().remove(getSessonId());
		return GGSuccessFuture.DEFAULT_SUCCESS_FUTURE;
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
	public C getConfig() {
		return config;
	}
	
	public void setConfig(C config) {
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

	@Override
	public IMetadata getMetadata() {
		return this.metadata;
	}
	
	public void setMetadata(IMetadata metadata) {
		this.metadata = metadata;
	}

}
