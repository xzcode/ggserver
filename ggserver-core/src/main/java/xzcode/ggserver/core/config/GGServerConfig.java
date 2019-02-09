package xzcode.ggserver.core.config;

import java.util.Arrays;

import xzcode.ggserver.core.component.GGComponentManager;
import xzcode.ggserver.core.event.EventInvokerManager;
import xzcode.ggserver.core.handler.serializer.ISerializer;
import xzcode.ggserver.core.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.message.filter.MessageFilterManager;
import xzcode.ggserver.core.message.receive.RequestMessageManager;
import xzcode.ggserver.core.message.receive.executor.RequestMessageTaskExecutor;
import xzcode.ggserver.core.message.send.SendMessageManager;
import xzcode.ggserver.core.session.UserSessonManager;

/**
 * socket 服务器配置类
 *
 * @author zai
 * 2017-08-13 19:03:31
 */
public class GGServerConfig {
	
	private boolean 	enabled = false;

	private boolean 	autoRun = false;

	private String[] 	scanPackage;

	private String 		host = "0.0.0.0";

	private int 		port = 9999;

	private int 		bossThreadSize = 0;
	
	private int 		workThreadSize = 0;

	private int 		reqTaskCorePoolSize = 10;
	private int 		reqTaskMaxPoolSize = 100;
	private long 		reqTaskKeepAliveTime = 10000;
	private int 		reqTaskTaskQueueSize = 100;
	
	private int 		timeoutTaskCorePoolSize = 10;
	private int 		timeoutTaskMaxPoolSize = 100;
	private int 		timeoutTaskTaskQueueSize = 100;


	private boolean 	idleCheckEnabled = true;
	private long 		readerIdleTime = 5000;
	private long 		writerIdleTime = 5000;
	private long 		allIdleTime = 5000;
	
	private int 		maxDataLength = 1024 * 1024 * 5;

	private String 		serverType = ServerTypeConstants.SOCKET;

	private String 		serializerType = SerializerFactory.SerializerType.MESSAGE_PACK;

	private String 		websocketPath = "/websocket";
	
	private int 		httpMaxContentLength = 65536;
	
	private boolean		useSSL = false;
	
	private ISerializer serializer;
	
	private GGComponentManager componentManager = new GGComponentManager();
	private RequestMessageManager requestMessageManager = new RequestMessageManager();
	private SendMessageManager sendMessageManager = new SendMessageManager(this);
	private MessageFilterManager messageFilterManager = new MessageFilterManager();
	private EventInvokerManager eventInvokerManager = new EventInvokerManager();
	private UserSessonManager userSessonManager = new UserSessonManager();
	
	private RequestMessageTaskExecutor requestMessageTaskExecutor;
	
	
	public static interface ServerTypeConstants{
		
		String SOCKET = "socket";
		
		String WEBSOCKET = "websocket";
		
	}
	
	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}
	
	public ISerializer getSerializer() {
		return serializer;
	}
	
	public void setTaskExecutor(RequestMessageTaskExecutor taskExecutor) {
		this.requestMessageTaskExecutor = taskExecutor;
	}
	public RequestMessageTaskExecutor getTaskExecutor() {
		return requestMessageTaskExecutor;
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
	public UserSessonManager getUserSessonManager() {
		return userSessonManager;
	}
	public void setUserSessonManager(UserSessonManager userSessonManager) {
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

	public int getReqTaskCorePoolSize() {
		return reqTaskCorePoolSize;
	}

	public void setReqTaskCorePoolSize(int reqTaskCorePoolSize) {
		this.reqTaskCorePoolSize = reqTaskCorePoolSize;
	}

	public int getReqTaskMaxPoolSize() {
		return reqTaskMaxPoolSize;
	}

	public void setReqTaskMaxPoolSize(int reqTaskMaxPoolSize) {
		this.reqTaskMaxPoolSize = reqTaskMaxPoolSize;
	}

	public long getReqTaskKeepAliveTime() {
		return reqTaskKeepAliveTime;
	}

	public void setReqTaskKeepAliveTime(long reqTaskKeepAliveTime) {
		this.reqTaskKeepAliveTime = reqTaskKeepAliveTime;
	}

	public int getReqTaskTaskQueueSize() {
		return reqTaskTaskQueueSize;
	}

	public void setReqTaskTaskQueueSize(int reqTaskTaskQueueSize) {
		this.reqTaskTaskQueueSize = reqTaskTaskQueueSize;
	}

	public RequestMessageTaskExecutor getRequestMessageTaskExecutor() {
		return requestMessageTaskExecutor;
	}

	public GGComponentManager getComponentManager() {
		return componentManager;
	}
	
	public void setComponentManager(GGComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	

}
