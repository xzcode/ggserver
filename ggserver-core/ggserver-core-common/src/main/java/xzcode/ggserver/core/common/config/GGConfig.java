package xzcode.ggserver.core.common.config;

import java.nio.charset.Charset;
import java.util.concurrent.ThreadFactory;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.event.impl.DefaultEventManager;
import xzcode.ggserver.core.common.executor.DefaultTaskExecutor;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.thread.SimpleThreadFactory;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.impl.DefaultFilterManager;
import xzcode.ggserver.core.common.handler.codec.IDecodeHandler;
import xzcode.ggserver.core.common.handler.codec.IEncodeHandler;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultDecodeHandler;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultEncodeHandler;
import xzcode.ggserver.core.common.handler.pack.IReceivePackHandler;
import xzcode.ggserver.core.common.handler.pack.impl.DefaultReceivePackHandler;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.common.message.meta.provider.IMetadataProvider;
import xzcode.ggserver.core.common.message.meta.provider.VoidMetadataProvider;
import xzcode.ggserver.core.common.message.meta.resolver.IMetadataResolver;
import xzcode.ggserver.core.common.message.meta.resolver.VoidMetadataResolver;
import xzcode.ggserver.core.common.message.request.manager.IRequestMessageManager;
import xzcode.ggserver.core.common.message.request.manager.RequestMessageManager;
import xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;
import xzcode.ggserver.core.common.session.factory.ISessionFactory;
import xzcode.ggserver.core.common.session.id.DefaultSessionIdGenerator;
import xzcode.ggserver.core.common.session.id.ISessionIdGenerator;
import xzcode.ggserver.core.common.session.manager.DefaultSessionManager;
import xzcode.ggserver.core.common.session.manager.ISessionManager;

/**
 * GGServer配置类
 * 
 * @author zai
 * 2019-10-02 22:10:07
 */
public class GGConfig {
	
	protected boolean 	enabled = false;
	
	//是否已初始化
	protected boolean 	inited = false;

	protected boolean 	autoShutdown = true;

	protected int 		bossThreadSize = 0;
	
	protected int 		workThreadSize = 0;
	
	protected int 		taskThreadSize = 0;

	protected boolean 	idleCheckEnabled = true;
	
	protected long 		readerIdleTime = 5000;
	
	protected long 		writerIdleTime = 5000;
	
	protected long 		allIdleTime = 5000;
	
	protected int 		maxDataLength = 1024 * 1024 * 5;

	protected String 	protocolType = ProtocolTypeConstants.MIXED;

	protected String 	serializerType = SerializerFactory.SerializerType.PROTO_STUFF;

	protected String 	websocketPath = "/websocket";
	
	protected Charset	charset = Charset.forName("utf-8");
	
	protected boolean	useSSL = false;
	
	protected int 		soBacklog = 1024;
	
	protected boolean 	soReuseaddr = false;
	
	protected long 		sessionExpireMs = 2L * 60L * 1000L;
	
	protected ISessionFactory sessionFactory;
	protected ISessionIdGenerator sessionIdGenerator;
	
	protected ISerializer serializer = SerializerFactory.geSerializer(serializerType);
	
	protected IDecodeHandler decodeHandler;
	protected IEncodeHandler encodeHandler;
	
	protected IReceivePackHandler receivePackHandler;
	
	protected IRequestMessageManager requestMessageManager;
	protected IFilterManager filterManager;
	protected IEventManager eventManager;
	protected ISessionManager sessionManager;
	
	protected IMetadataResolver<?> metadataResolver;
	
	protected IMetadataProvider<?> metadataProvider;
	
    
	protected NioEventLoopGroup workerGroup;
	
	
	protected ITaskExecutor taskExecutor;
	
	
	protected ThreadFactory workerGroupThreadFactory;

	
	public void init() {
		requestMessageManager = new RequestMessageManager();
		filterManager = new DefaultFilterManager();
		eventManager = new DefaultEventManager();
		
		if (workerGroupThreadFactory == null) {
			workerGroupThreadFactory = new SimpleThreadFactory("netty-worker-", false);
		}
		if (workerGroup == null) {
			workerGroup = new NioEventLoopGroup(getWorkThreadSize(),getWorkerGroupThreadFactory());	
		}
		
		if (taskExecutor == null) {
			taskExecutor = new DefaultTaskExecutor(getTaskThreadSize());
		}
		
		if (decodeHandler == null) {
			decodeHandler = new DefaultDecodeHandler(this);
		}
		if (encodeHandler == null) {
			encodeHandler = new DefaultEncodeHandler(this);
		}
		
		if (metadataResolver == null) {
			metadataResolver = new VoidMetadataResolver();
		}
		
		if (metadataProvider == null) {
			metadataProvider = new VoidMetadataProvider();
		}
		
		if (receivePackHandler == null) {
			receivePackHandler = new DefaultReceivePackHandler(this);
		}
		if (serializer == null) {
			serializer = SerializerFactory.geSerializer(serializerType);
		}
		
		if (sessionManager == null) {
			sessionManager = new DefaultSessionManager(this);			
		}
		
		if (sessionFactory == null) {
			sessionFactory = new DefaultChannelSessionFactory(this);
		}
		if (sessionIdGenerator == null) {
			sessionIdGenerator = new DefaultSessionIdGenerator();
		}
		
		this.inited = true;
	}
	
	public GGConfig() {
		super();
	}

	public NioEventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

	public void setWorkerGroup(NioEventLoopGroup workerGroup) {
		this.workerGroup = workerGroup;
	}

	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}
	
	public ISerializer getSerializer() {
		return serializer;
	}
	
	
	public boolean isEnabled() {
		return enabled;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getBossThreadSize() {
		return bossThreadSize;
	}
	public void setBossThreadSize(int bossThreadSize) {
		this.bossThreadSize = bossThreadSize;
	}
	public int getWorkThreadSize() {
		return workThreadSize;
	}
	public void setWorkThreadSize(int workThreadSize) {
		this.workThreadSize = workThreadSize;
	}
	
	public int getTaskThreadSize() {
		return taskThreadSize;
	}
	
	public void setTaskThreadSize(int taskThreadSize) {
		this.taskThreadSize = taskThreadSize;
	}
	
	public long getReaderIdleTime() {
		return readerIdleTime;
	}
	public void setReaderIdleTime(long readerIdleTime) {
		this.readerIdleTime = readerIdleTime;
	}
	public long getWriterIdleTime() {
		return writerIdleTime;
	}
	public void setWriterIdleTime(long writerIdleTime) {
		this.writerIdleTime = writerIdleTime;
	}
	public long getAllIdleTime() {
		return allIdleTime;
	}
	public void setAllIdleTime(long allIdleTime) {
		this.allIdleTime = allIdleTime;
	}
	public boolean isIdleCheckEnabled() {
		return idleCheckEnabled;
	}
	public boolean getIdleCheckEnabled() {
		return idleCheckEnabled;
	}
	public void setIdleCheckEnabled(boolean idleCheckEnabled) {
		this.idleCheckEnabled = idleCheckEnabled;
	}
	
	
	public String getProtocolType() {
		return protocolType;
	}
	
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	
	public String getWebsocketPath() {
		return websocketPath;
	}
	
	public void setWebsocketPath(String websocketPath) {
		this.websocketPath = websocketPath;
	}
	
	public String getSerializerType() {
		return serializerType;
	}
	
	public void setSerializerType(String serializerType) {
		if (serializerType == null || serializerType == "") {
			return;
		}
		this.serializerType = serializerType;
	}
	
	

	public IEventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(IEventManager eventManager) {
		this.eventManager = eventManager;
	}
	public IRequestMessageManager getRequestMessageManager() {
		return requestMessageManager;
	}
	public void setRequestMessageManager(IRequestMessageManager requestMessageManager) {
		this.requestMessageManager = requestMessageManager;
	}
	public boolean isUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	public ISessionManager getSessionManager() {
		return sessionManager;
	}


	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	
	public IFilterManager getFilterManager() {
		return filterManager;
	}

	public void setFilterManager(IFilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public int getMaxDataLength() {
		return maxDataLength;
	}
	
	public void setMaxDataLength(int maxDataLength) {
		this.maxDataLength = maxDataLength;
	}

	public ITaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	public void setTaskExecutor(ITaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public IDecodeHandler getDecodeHandler() {
		return decodeHandler;
	}


	public void setDecodeHandler(IDecodeHandler decodeHandler) {
		this.decodeHandler = decodeHandler;
	}


	public IEncodeHandler getEncodeHandler() {
		return encodeHandler;
	}


	public void setEncodeHandler(IEncodeHandler encodeHandler) {
		this.encodeHandler = encodeHandler;
	}


	public ThreadFactory getWorkerGroupThreadFactory() {
		return workerGroupThreadFactory;
	}


	public void setWorkerGroupThreadFactory(ThreadFactory workerGroupThreadFactory) {
		this.workerGroupThreadFactory = workerGroupThreadFactory;
	}


	public IReceivePackHandler getReceivePackHandler() {
		return receivePackHandler;
	}


	public void setReceivePackHandler(IReceivePackHandler receivePackHandler) {
		this.receivePackHandler = receivePackHandler;
	}


	public boolean isAutoShutdown() {
		return autoShutdown;
	}

	public void setAutoShutdown(boolean autoShutdown) {
		this.autoShutdown = autoShutdown;
	}

	public int getSoBacklog() {
		return soBacklog;
	}

	public void setSoBacklog(int soBacklog) {
		this.soBacklog = soBacklog;
	}

	public boolean isInited() {
		return inited;
	}

	public void setInited(boolean inited) {
		this.inited = inited;
	}
	
	public ISessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(ISessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public long getSessionExpireMs() {
		return sessionExpireMs;
	}

	public void setSessionExpireMs(long sessionExpireMs) {
		this.sessionExpireMs = sessionExpireMs;
	}

	public boolean isSoReuseaddr() {
		return soReuseaddr;
	}

	public void setSoReuseaddr(boolean soReuseaddr) {
		this.soReuseaddr = soReuseaddr;
	}
	
	public IMetadataResolver<?> getMetadataResolver() {
		return metadataResolver;
	}
	
	public void setMetadataResolver(IMetadataResolver<?> metadataResolver) {
		this.metadataResolver = metadataResolver;
	}
	
	public IMetadataProvider<?> getMetadataProvider() {
		return metadataProvider;
	}
	
	public void setMetadataProvider(IMetadataProvider<?> metadataProvider) {
		this.metadataProvider = metadataProvider;
	}
	
	public ISessionIdGenerator getSessionIdGenerator() {
		return sessionIdGenerator;
	}
	
	public void setSessionIdGenerator(ISessionIdGenerator sessionIdGenerator) {
		this.sessionIdGenerator = sessionIdGenerator;
	}
}
