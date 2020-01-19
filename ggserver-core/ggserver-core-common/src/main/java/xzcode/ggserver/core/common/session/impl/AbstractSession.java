package xzcode.ggserver.core.common.session.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.future.GGNettyFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.meta.provider.IMetadataProvider;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.listener.ISessionDisconnectListener;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public abstract class AbstractSession<C extends GGConfig> implements GGSession {
	
	protected C config;//具体的配置
	protected long expireMs;//超时时间
	protected boolean expired = false;//是否已超时
	protected String sessionId;//sessionid
	protected String host;//远端地址
	protected int port;//远端端口号
	protected List<ISessionDisconnectListener> disconnectListeners = new ArrayList<>();
	
	public AbstractSession(String sessionId, C config) {
		this.config = config;
		this.sessionId = sessionId;
		this.updateExpire();
	}

	public C getConfig() {
		return this.config;
	}

	@Override
	public IFilterManager getFilterManager() {
		return getConfig().getFilterManager();
	}

	@Override
	public ITaskExecutor getTaskExecutor() {
		return getConfig().getTaskExecutor();
	}

	@Override
	public Charset getCharset() {
		return getConfig().getCharset();
	}

	@Override
	public ISerializer getSerializer() {
		return getConfig().getSerializer();
	}

	@Override
	public IMetadataProvider<?> getMetadataProvider() {
		return getConfig().getMetadataProvider();
	}
	

	@Override
	public IEventManager getEventManagerImpl() {
		return getConfig().getEventManager();
	}

	@Override
	public GGSession getSession() {
		return this;
	}
	
	@Override
	public IGGFuture disconnect() {
		return new GGNettyFuture(this.getChannel().close());
	}
	
	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public int getPort() {
		return this.port;
	}
	
	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
		
	}
	@Override
	public void expire() {
		this.expired = true;
	}

	@Override
	public boolean isExpired() {
		if (!this.expired) {
			this.expired = expireMs < System.currentTimeMillis();
		}
		return this.expired;
	}

	@Override
	public void updateExpire() {
		this.expireMs = System.currentTimeMillis() + config.getSessionExpireMs();
	}
	@Override
	public String getSessonId() {
		return this.sessionId;
	}


}
