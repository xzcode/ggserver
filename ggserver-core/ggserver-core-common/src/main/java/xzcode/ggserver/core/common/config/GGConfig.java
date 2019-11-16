package xzcode.ggserver.core.common.config;

import java.nio.charset.Charset;
import java.util.concurrent.ThreadFactory;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.common.channel.group.IChannelGroupManager;
import xzcode.ggserver.core.common.channel.group.impl.GGChannelGroupManager;
import xzcode.ggserver.core.common.component.GGComponentManager;
import xzcode.ggserver.core.common.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.common.constant.GGServerTypeConstants;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.event.impl.DefaultEventManager;
import xzcode.ggserver.core.common.executor.factory.EventLoopGroupThreadFactory;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.impl.DefaultFilterManager;
import xzcode.ggserver.core.common.handler.codec.IGGDecodeHandler;
import xzcode.ggserver.core.common.handler.codec.IGGEncodeHandler;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultDecodeHandler;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultEncodeHandler;
import xzcode.ggserver.core.common.handler.pack.IGGReceivePackHandler;
import xzcode.ggserver.core.common.handler.pack.impl.DefaultReceivePackHandler;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.common.message.receive.RequestMessageManager;
import xzcode.ggserver.core.common.message.send.SendMessageManager;
import xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;
import xzcode.ggserver.core.common.session.factory.ISessionFactory;
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

	protected String[] 	scanPackage;

	

	protected int 		bossThreadSize = 0;
	
	protected int 		workThreadSize = 32;

	protected boolean 	idleCheckEnabled = true;
	
	protected long 		readerIdleTime = 5000;
	
	protected long 		writerIdleTime = 5000;
	
	protected long 		allIdleTime = 5000;
	
	protected int 		maxDataLength = 1024 * 1024 * 5;

	protected String 	serverType = GGServerTypeConstants.MIXED;

	protected String 	serializerType = SerializerFactory.SerializerType.PROTO_STUFF;

	protected String 	websocketPath = "/websocket";
	
	protected Charset	charset = Charset.forName("utf-8");
	
	protected boolean	useSSL = false;
	
	protected int 		soBacklog = 1024;
	
	protected long 		sessionExpireMs = 2L * 60L * 1000L;
	
	protected IChannelGroupManager channelGroupManager;
	
	protected ISessionFactory sessionFactory;
	
	protected ISerializer serializer;
	
	protected IGGDecodeHandler decodeHandler;
	protected IGGEncodeHandler encodeHandler;
	
	protected IGGReceivePackHandler receivePackHandler;
	
	protected GGComponentManager 	componentManager;
	protected RequestMessageManager requestMessageManager;
	protected SendMessageManager sendMessageManager;
	protected IFilterManager filterManager;
	protected IEventManager eventManager;
	protected ISessionManager sessionManager;
	
    
	protected NioEventLoopGroup workerGroup;
	protected ThreadFactory workerGroupThreadFactory;
	
	public void init() {
		componentManager = new GGComponentManager();
		requestMessageManager = new RequestMessageManager();
		sendMessageManager = new SendMessageManager(this);
		filterManager = new DefaultFilterManager();
		eventManager = new DefaultEventManager();
		if (sessionManager == null) {
			sessionManager = new DefaultSessionManager(this);			
		}
		
		if (sessionFactory == null) {
			sessionFactory = new DefaultChannelSessionFactory(this);
		}
		
		if (channelGroupManager == null) {
			channelGroupManager = new GGChannelGroupManager(this);
		}
		
		if (workerGroupThreadFactory == null) {
			workerGroupThreadFactory = new EventLoopGroupThreadFactory("netty-worker");
		}
		if (workerGroup == null) {
			workerGroup = new NioEventLoopGroup(getWorkThreadSize(),workerGroupThreadFactory);	
		}
		
		if (decodeHandler == null) {
			decodeHandler = new DefaultDecodeHandler(this);
		}
		if (encodeHandler == null) {
			encodeHandler = new DefaultEncodeHandler(this);
		}
		
		if (receivePackHandler == null) {
			receivePackHandler = new DefaultReceivePackHandler(this);
		}
		if (serializer == null) {
			serializer = SerializerFactory.geSerializer(serializerType);
		}
		
		if (this.getScanPackage() != null && this.getScanPackage().length > 0) {
    		GGComponentScanner.scan(this);
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
	public String[] getScanPackage() {
		return scanPackage;
	}
	public void setScanPackage(String...scanPackage) {
		this.scanPackage = scanPackage;
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
	
	
	public String getServerType() {
		return serverType;
	}
	
	public void setServerType(String serverType) {
		this.serverType = serverType;
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

	public GGComponentManager getComponentObjectManager() {
		return componentManager;
	}
	public void setComponentObjectMapper(GGComponentManager componentObjectMapper) {
		this.componentManager = componentObjectMapper;
	}
	public RequestMessageManager getRequestMessageManager() {
		return requestMessageManager;
	}
	public void setRequestMessageManager(RequestMessageManager requestMessageManager) {
		this.requestMessageManager = requestMessageManager;
	}
	public boolean isUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	public RequestMessageManager getMessageInvokerManager() {
		return requestMessageManager;
	}
	public void setMessageInvokerManager(RequestMessageManager requestMessageManager) {
		this.requestMessageManager = requestMessageManager;
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
	public SendMessageManager getSendMessageManager() {
		return sendMessageManager;
	}
	
	public void setSendMessageManager(SendMessageManager sendMessageManager) {
		this.sendMessageManager = sendMessageManager;
	}

	public GGComponentManager getComponentManager() {
		return componentManager;
	}
	
	public void setComponentManager(GGComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	public NioEventLoopGroup getTaskExecutor() {
		return workerGroup;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public IGGDecodeHandler getDecodeHandler() {
		return decodeHandler;
	}


	public void setDecodeHandler(IGGDecodeHandler decodeHandler) {
		this.decodeHandler = decodeHandler;
	}


	public IGGEncodeHandler getEncodeHandler() {
		return encodeHandler;
	}


	public void setEncodeHandler(IGGEncodeHandler encodeHandler) {
		this.encodeHandler = encodeHandler;
	}


	public ThreadFactory getWorkerGroupThreadFactory() {
		return workerGroupThreadFactory;
	}


	public void setWorkerGroupThreadFactory(ThreadFactory workerGroupThreadFactory) {
		this.workerGroupThreadFactory = workerGroupThreadFactory;
	}


	public IGGReceivePackHandler getReceivePackHandler() {
		return receivePackHandler;
	}


	public void setReceivePackHandler(IGGReceivePackHandler receivePackHandler) {
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

	public IChannelGroupManager getChannelGroupManager() {
		return channelGroupManager;
	}

	public void setChannelGroupManager(IChannelGroupManager channelGroupManager) {
		this.channelGroupManager = channelGroupManager;
	}

	public long getSessionExpireMs() {
		return sessionExpireMs;
	}

	public void setSessionExpireMs(long sessionExpireMs) {
		this.sessionExpireMs = sessionExpireMs;
	}
	
	
}
