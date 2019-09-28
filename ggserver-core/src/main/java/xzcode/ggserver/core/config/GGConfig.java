package xzcode.ggserver.core.config;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.component.GGComponentManager;
import xzcode.ggserver.core.constant.GGServerTypeConstants;
import xzcode.ggserver.core.event.EventInvokerManager;
import xzcode.ggserver.core.executor.factory.EventLoopGroupThreadFactory;
import xzcode.ggserver.core.handler.serializer.ISerializer;
import xzcode.ggserver.core.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.message.filter.MessageFilterManager;
import xzcode.ggserver.core.message.receive.RequestMessageManager;
import xzcode.ggserver.core.message.send.SendMessageManager;
import xzcode.ggserver.core.session.GGUserSessonManager;

/**
 * socket 服务器配置类
 *
 * @author zai
 * 2017-08-13 19:03:31
 */
public class GGConfig {
	
	private boolean 	enabled = false;

	private boolean 	autoRun = false;
	
	//是否客户端
	private boolean 	client = false;
	
	private String[] 	scanPackage;

	private String 		host = "0.0.0.0";

	private int 		port = 9999;

	private int 		bossThreadSize = 0;
	
	private int 		workThreadSize = 0;

	private boolean 	idleCheckEnabled = true;
	
	private long 		readerIdleTime = 5000;
	
	private long 		writerIdleTime = 5000;
	
	private long 		allIdleTime = 5000;
	
	private int 		maxDataLength = 1024 * 1024 * 5;

	private String 		serverType = GGServerTypeConstants.MIXED;

	private String 		serializerType = SerializerFactory.SerializerType.MESSAGE_PACK;

	private String 		websocketPath = "/websocket";
	
	private String 		charset = "utf-8";
	
	private int 		httpMaxContentLength = 65536;
	
	private boolean		useSSL = false;
	
	private ISerializer serializer;
	
	
	private GGComponentManager 	componentManager;
	private RequestMessageManager requestMessageManager;
	private SendMessageManager sendMessageManager;
	private MessageFilterManager messageFilterManager;
	private EventInvokerManager eventInvokerManager;
	private GGUserSessonManager userSessonManager;
	
	private NioEventLoopGroup bossGroup;
    
	private NioEventLoopGroup workerGroup;
	
	public void init() {
		componentManager = new GGComponentManager();
		requestMessageManager = new RequestMessageManager();
		sendMessageManager = new SendMessageManager(this);
		messageFilterManager = new MessageFilterManager();
		eventInvokerManager = new EventInvokerManager();
		userSessonManager = new GGUserSessonManager();
		
		if (!isClient()) {
			bossGroup = new NioEventLoopGroup(getBossThreadSize(),new EventLoopGroupThreadFactory("netty-boss"));
		}
	    
		workerGroup = new NioEventLoopGroup(getWorkThreadSize(),new EventLoopGroupThreadFactory("netty-worker"));
	}
	
	
	public GGConfig() {
		super();
		init();
	}
	
	


	public GGConfig(boolean client) {
		super();
		this.client = client;
		init();
	}


	public boolean isClient() {
		return client;
	}

	public void setClient(boolean client) {
		this.client = client;
	}

	public NioEventLoopGroup getBossGroup() {
		return bossGroup;
	}

	public void setBossGroup(NioEventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
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
	public boolean isAutoRun() {
		return autoRun;
	}
	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
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
	
	public int getHttpMaxContentLength() {
		return httpMaxContentLength;
	}
	
	public void setHttpMaxContentLength(int httpMaxContentLength) {
		this.httpMaxContentLength = httpMaxContentLength;
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
	public EventInvokerManager getEventInvokerManager() {
		return eventInvokerManager;
	}
	public void setEventInvokerManager(EventInvokerManager eventInvokerManager) {
		this.eventInvokerManager = eventInvokerManager;
	}
	public GGUserSessonManager getUserSessonManager() {
		return userSessonManager;
	}
	public void setUserSessonManager(GGUserSessonManager userSessonManager) {
		this.userSessonManager = userSessonManager;
	}
	
	public MessageFilterManager getMessageFilterManager() {
		return messageFilterManager;
	}
	
	public void setMessageFilterManager(MessageFilterManager messageFilterManager) {
		this.messageFilterManager = messageFilterManager;
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
	
	
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
