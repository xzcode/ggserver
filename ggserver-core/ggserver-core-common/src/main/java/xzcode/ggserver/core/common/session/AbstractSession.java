package xzcode.ggserver.core.common.session;

import java.nio.charset.Charset;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.meta.provider.IMetadataProvider;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public abstract class AbstractSession<C extends GGConfig> implements GGSession {
	
	protected C config;
	protected long expireMs;
	protected String sessionId;
	protected String host;
	protected int port;
	
	public AbstractSession(String sessionId, C config) {
		this.config = config;
		this.sessionId = sessionId;
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
	public GGSession getSession() {
		return this;
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
	public boolean isExpired() {
		return expireMs < System.currentTimeMillis();
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
