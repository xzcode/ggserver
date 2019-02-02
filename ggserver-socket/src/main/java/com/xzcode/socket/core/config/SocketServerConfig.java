package com.xzcode.socket.core.config;

import java.util.Arrays;

import com.xzcode.socket.core.component.SocketComponentObjectManager;
import com.xzcode.socket.core.event.EventInvokerManager;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.filter.MessageFilterManager;
import com.xzcode.socket.core.message.MessageInvokerManager;
import com.xzcode.socket.core.serializer.ISerializer;
import com.xzcode.socket.core.serializer.factory.SerializerFactory;
import com.xzcode.socket.core.session.UserSessonManager;

/**
 * socket 服务器配置类
 *
 * @author zai
 * 2017-08-13 19:03:31
 */
public class SocketServerConfig {
	
	private boolean 	enabled = false;

	private boolean 	autoRun = false;

	private String 		host = "0.0.0.0";

	private int 		port = 9999;

	private int 		bossThreadSize = 0;
	private int 		workThreadSize = 0;

	private int 		corePoolSize = 10;
	private int 		maximumPoolSize = 100;
	private long 		keepAliveTime = 10000;
	private int 		taskQueueSize = 1000;

	private String[] 	scanPackage;

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
	
	private SocketComponentObjectManager componentObjectManager = new SocketComponentObjectManager();
	private MessageInvokerManager messageInvokerManager = new MessageInvokerManager();
	private MessageFilterManager messageFilterManager = new MessageFilterManager();
	private EventInvokerManager eventInvokerManager = new EventInvokerManager();
	private UserSessonManager userSessonManager = new UserSessonManager();
	
	private SocketServerTaskExecutor taskExecutor;
	
	
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
	
	public void setTaskExecutor(SocketServerTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public SocketServerTaskExecutor getTaskExecutor() {
		return taskExecutor;
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
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public long getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getTaskQueueSize() {
		return taskQueueSize;
	}
	
	public void setTaskQueueSize(int taskQueueSize) {
		this.taskQueueSize = taskQueueSize;
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
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocketServerConfig [enabled=");
		builder.append(enabled);
		builder.append(", autoRun=");
		builder.append(autoRun);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", bossThreadSize=");
		builder.append(bossThreadSize);
		builder.append(", workThreadSize=");
		builder.append(workThreadSize);
		builder.append(", corePoolSize=");
		builder.append(corePoolSize);
		builder.append(", maximumPoolSize=");
		builder.append(maximumPoolSize);
		builder.append(", keepAliveTime=");
		builder.append(keepAliveTime);
		builder.append(", taskQueueSize=");
		builder.append(taskQueueSize);
		builder.append(", scanPackage=");
		builder.append(Arrays.toString(scanPackage));
		builder.append(", idleCheckEnabled=");
		builder.append(idleCheckEnabled);
		builder.append(", readerIdleTime=");
		builder.append(readerIdleTime);
		builder.append(", writerIdleTime=");
		builder.append(writerIdleTime);
		builder.append(", allIdleTime=");
		builder.append(allIdleTime);
		builder.append(", serverType=");
		builder.append(serverType);
		builder.append(", serializerType=");
		builder.append(serializerType);
		builder.append(", websocketPath=");
		builder.append(websocketPath);
		builder.append(", httpMaxContentLength=");
		builder.append(httpMaxContentLength);
		builder.append(", useSSL=");
		builder.append(useSSL);
		builder.append(", componentObjectMapper=");
		builder.append(componentObjectManager);
		builder.append(", messageInvokerManager=");
		builder.append(messageInvokerManager);
		builder.append(", eventMethodInvoker=");
		builder.append(eventInvokerManager);
		builder.append(", userSessonManager=");
		builder.append(userSessonManager);
		builder.append("]");
		return builder.toString();
	}
	public SocketComponentObjectManager getComponentObjectManager() {
		return componentObjectManager;
	}
	public void setComponentObjectMapper(SocketComponentObjectManager componentObjectMapper) {
		this.componentObjectManager = componentObjectMapper;
	}
	public MessageInvokerManager getMessageMethodInvokeManager() {
		return messageInvokerManager;
	}
	public void setMessageMethodInvokeManager(MessageInvokerManager messageInvokerManager) {
		this.messageInvokerManager = messageInvokerManager;
	}
	public boolean isUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	public MessageInvokerManager getMessageInvokerManager() {
		return messageInvokerManager;
	}
	public void setMessageInvokerManager(MessageInvokerManager messageInvokerManager) {
		this.messageInvokerManager = messageInvokerManager;
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
	

}
