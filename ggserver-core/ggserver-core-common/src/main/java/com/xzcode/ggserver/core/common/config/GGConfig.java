package com.xzcode.ggserver.core.common.config;

import java.nio.charset.Charset;
import java.util.concurrent.ThreadFactory;

import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.event.EventManager;
import com.xzcode.ggserver.core.common.event.impl.DefaultEventManager;
import com.xzcode.ggserver.core.common.executor.DefaultTaskExecutor;
import com.xzcode.ggserver.core.common.executor.TaskExecutor;
import com.xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import com.xzcode.ggserver.core.common.filter.FilterManager;
import com.xzcode.ggserver.core.common.filter.impl.DefaultFilterManager;
import com.xzcode.ggserver.core.common.handler.codec.IDecodeHandler;
import com.xzcode.ggserver.core.common.handler.codec.IEncodeHandler;
import com.xzcode.ggserver.core.common.handler.codec.impl.DefaultDecodeHandler;
import com.xzcode.ggserver.core.common.handler.codec.impl.DefaultEncodeHandler;
import com.xzcode.ggserver.core.common.handler.pack.IReceivePackHandler;
import com.xzcode.ggserver.core.common.handler.pack.impl.DefaultReceivePackHandler;
import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.handler.serializer.factory.SerializerFactory;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPing;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPong;
import com.xzcode.ggserver.core.common.message.request.manager.DefaultRequestMessageManager;
import com.xzcode.ggserver.core.common.message.request.manager.ReceiveMessageManager;
import com.xzcode.ggserver.core.common.session.factory.ChannelSessionFactory;
import com.xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;
import com.xzcode.ggserver.core.common.session.id.DefaultSessionIdGenerator;
import com.xzcode.ggserver.core.common.session.id.ISessionIdGenerator;
import com.xzcode.ggserver.core.common.session.manager.DefaultSessionManager;
import com.xzcode.ggserver.core.common.session.manager.ISessionManager;
import com.xzcode.ggserver.core.common.utils.logger.PackLogger;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * GGServer配置类
 * 
 * @author zai 2019-10-02 22:10:07
 */
public class GGConfig {

	protected boolean enabled = false;

	// 是否已初始化
	protected boolean inited = false;

	protected boolean autoShutdown = true;

	protected int bossThreadSize = 0;

	protected int workThreadSize = 0;

	protected int taskThreadSize = 0;

	protected boolean idleCheckEnabled = true;

	protected long readerIdleTime = 5000;

	protected long writerIdleTime = 5000;

	protected long allIdleTime = 5000;

	protected int maxDataLength = 8 * 1024;

	protected String protocolType = ProtocolTypeConstants.MIXED;

	protected String serializerType = SerializerFactory.SerializerType.PROTO_STUFF;

	protected String websocketPath = "/websocket";

	protected Charset charset = Charset.forName("utf-8");

	protected boolean useSSL = false;

	protected int soBacklog = 1024;

	protected boolean soReuseaddr = true;

	protected long sessionExpireMs = 24L * 3600L * 1000L;

	protected boolean pingPongEnabled = false;

	protected boolean printPingPongInfo = false;

	private int pingPongLostTimes = 0; // 心跳失败次数

	private int pingPongMaxLoseTimes = 3;// 最大心跳失败允许次数

	protected ChannelSessionFactory sessionFactory;
	protected ISessionIdGenerator sessionIdGenerator;

	protected ISerializer serializer = SerializerFactory.geSerializer(serializerType);

	protected IDecodeHandler decodeHandler;
	protected IEncodeHandler encodeHandler;

	protected IReceivePackHandler receivePackHandler;

	protected ReceiveMessageManager requestMessageManager;
	protected FilterManager filterManager;
	protected EventManager eventManager;
	protected ISessionManager sessionManager;

	protected EventLoopGroup workerGroup;


	protected TaskExecutor taskExecutor;

	protected ThreadFactory workerGroupThreadFactory;

	protected boolean useSessionGroup = false;
	
	protected boolean enablePackLogger = true;
	
	protected PackLogger packLogger = new PackLogger(this);

	public void init() {
		requestMessageManager = new DefaultRequestMessageManager();
		filterManager = new DefaultFilterManager();
		eventManager = new DefaultEventManager();

		if (workerGroupThreadFactory == null) {
			workerGroupThreadFactory = new GGThreadFactory("gg-worker-", false);
		}
		if (workerGroup == null) {
			workerGroup = new NioEventLoopGroup(getWorkThreadSize(), getWorkerGroupThreadFactory());
		}

		if (taskExecutor == null) {
			taskExecutor = new DefaultTaskExecutor(workerGroup);
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

		if (sessionManager == null) {
			sessionManager = new DefaultSessionManager(this);
		}

		if (sessionFactory == null) {
			sessionFactory = new DefaultChannelSessionFactory(this);
		}
		if (sessionIdGenerator == null) {
			sessionIdGenerator = new DefaultSessionIdGenerator();
		}
		
		if (isPingPongEnabled()) {
			if (!isPrintPingPongInfo()) {
				this.packLogger.addPackLogFilter(pack -> {
					String actionString = pack.getActionString();
					return !(actionString.equals(GGPing.ACTION_ID) || actionString.equals(GGPong.ACTION_ID));
				});
			}
		}

		this.inited = true;
	}

	public GGConfig() {
		super();
	}

	public EventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

	public void setWorkerGroup(EventLoopGroup workerGroup) {
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

	public EventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public ReceiveMessageManager getRequestMessageManager() {
		return requestMessageManager;
	}

	public void setRequestMessageManager(ReceiveMessageManager requestMessageManager) {
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

	public FilterManager getFilterManager() {
		return filterManager;
	}

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public int getMaxDataLength() {
		return maxDataLength;
	}

	public void setMaxDataLength(int maxDataLength) {
		this.maxDataLength = maxDataLength;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
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

	public ChannelSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(ChannelSessionFactory sessionFactory) {
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

	public ISessionIdGenerator getSessionIdGenerator() {
		return sessionIdGenerator;
	}

	public void setSessionIdGenerator(ISessionIdGenerator sessionIdGenerator) {
		this.sessionIdGenerator = sessionIdGenerator;
	}


	public boolean isPingPongEnabled() {
		return pingPongEnabled;
	}

	public void setPingPongEnabled(boolean enablePingPong) {
		this.pingPongEnabled = enablePingPong;
	}

	public int getPingPongLostTimes() {
		return pingPongLostTimes;
	}

	public void setPingPongLostTimes(int lostTimes) {
		this.pingPongLostTimes = lostTimes;
	}

	public int getPingPongMaxLoseTimes() {
		return pingPongMaxLoseTimes;
	}

	public void setPingPongMaxLoseTimes(int maxLoseTimes) {
		this.pingPongMaxLoseTimes = maxLoseTimes;
	}

	public boolean isPrintPingPongInfo() {
		return printPingPongInfo;
	}

	public void setPrintPingPongInfo(boolean printPingPongInfo) {
		this.printPingPongInfo = printPingPongInfo;
	}
	
	public boolean isUseSessionGroup() {
		return useSessionGroup;
	}
	
	public void setUseSessionGroup(boolean useSessionGroup) {
		this.useSessionGroup = useSessionGroup;
	}

	public boolean isEnablePackLogger() {
		return enablePackLogger;
	}

	public void setEnablePackLogger(boolean enablePackLogger) {
		this.enablePackLogger = enablePackLogger;
	}

	public PackLogger getPackLogger() {
		return packLogger;
	}

	public void setPackLogger(PackLogger packLogger) {
		this.packLogger = packLogger;
	}
	
	
}
