package xzcode.ggserver.client.config;

import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.client.event.EventInvokerManager;
import xzcode.ggserver.client.executor.factory.EventLoopGroupThreadFactory;
import xzcode.ggserver.client.handler.serializer.ISerializer;
import xzcode.ggserver.client.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.client.message.receive.ClientOnMessageManager;
import xzcode.ggserver.client.message.send.ClientSendMessageManager;

/**
 * socket 服务器配置类
 *
 * @author zai
 * 2017-08-13 19:03:31
 */
public class GGClientConfig {
	
	private boolean 	enabled = false;

	private boolean 	autoRun = false;

	private String[] 	scanPackage;

	private String 		host = "127.0.0.1";

	private int 		port = 9999;

	private int 		workThreadSize = 1;

	private int 		executorCorePoolSize = 1;
	private int 		executorMaxPoolSize = 1;
	private long 		executorKeepAliveTime = 10000;
	private int 		executorTaskQueueSize = 100;
	

	private boolean 	idleCheckEnabled = true;
	private long 		readerIdleTime = 5000;
	private long 		writerIdleTime = 5000;
	private long 		allIdleTime = 5000;
	
	private int 		maxDataLength = 1024 * 1024 * 5;
	
	private String 		charset = "utf-8";

	private String 		serializerType = SerializerFactory.SerializerType.MESSAGE_PACK;

	private ISerializer serializer = SerializerFactory.geSerializer(serializerType);
	
	private Channel channel;
	
	
	
	private ClientOnMessageManager clientOnMessageManager = new ClientOnMessageManager();
	private ClientSendMessageManager clientSendMessageManager = new ClientSendMessageManager(this);
	private EventInvokerManager eventInvokerManager = new EventInvokerManager();
	
    
	private NioEventLoopGroup workerGroup;
	
	
	public GGClientConfig() {
	}

	public int getExecutorCorePoolSize() {
		return executorCorePoolSize;
	}

	public void setExecutorCorePoolSize(int executorCorePoolSize) {
		this.executorCorePoolSize = executorCorePoolSize;
	}

	public int getExecutorMaxPoolSize() {
		return executorMaxPoolSize;
	}

	public void setExecutorMaxPoolSize(int executorMaxPoolSize) {
		this.executorMaxPoolSize = executorMaxPoolSize;
	}

	public long getExecutorKeepAliveTime() {
		return executorKeepAliveTime;
	}

	public void setExecutorKeepAliveTime(long executorKeepAliveTime) {
		this.executorKeepAliveTime = executorKeepAliveTime;
	}

	public int getExecutorTaskQueueSize() {
		return executorTaskQueueSize;
	}

	public void setExecutorTaskQueueSize(int executorTaskQueueSize) {
		this.executorTaskQueueSize = executorTaskQueueSize;
	}

	public NioEventLoopGroup getWorkerGroup() {
		if (workerGroup == null) {
			synchronized (workerGroup) {
				workerGroup = new NioEventLoopGroup(getWorkThreadSize(), new EventLoopGroupThreadFactory("Worker Group"));
			}
		}
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
	public String getSerializerType() {
		return serializerType;
	}
	public void setSerializerType(String serializerType) {
		if (serializerType == null || serializerType == "") {
			return;
		}
		this.serializerType = serializerType;
	}
	public ClientOnMessageManager getRequestMessageManager() {
		return clientOnMessageManager;
	}
	public void setRequestMessageManager(ClientOnMessageManager clientOnMessageManager) {
		this.clientOnMessageManager = clientOnMessageManager;
	}
	public ClientOnMessageManager getMessageInvokerManager() {
		return clientOnMessageManager;
	}
	public void setMessageInvokerManager(ClientOnMessageManager clientOnMessageManager) {
		this.clientOnMessageManager = clientOnMessageManager;
	}
	public EventInvokerManager getEventInvokerManager() {
		return eventInvokerManager;
	}
	public void setEventInvokerManager(EventInvokerManager eventInvokerManager) {
		this.eventInvokerManager = eventInvokerManager;
	}
	
	public int getMaxDataLength() {
		return maxDataLength;
	}
	
	public void setMaxDataLength(int maxDataLength) {
		this.maxDataLength = maxDataLength;
	}
	public ClientSendMessageManager getSendMessageManager() {
		return clientSendMessageManager;
	}
	
	public void setSendMessageManager(ClientSendMessageManager clientSendMessageManager) {
		this.clientSendMessageManager = clientSendMessageManager;
	}

	public int getReqTaskCorePoolSize() {
		return executorCorePoolSize;
	}

	public void setReqTaskCorePoolSize(int reqTaskCorePoolSize) {
		this.executorCorePoolSize = reqTaskCorePoolSize;
	}

	public int getReqTaskMaxPoolSize() {
		return executorMaxPoolSize;
	}

	public void setReqTaskMaxPoolSize(int reqTaskMaxPoolSize) {
		this.executorMaxPoolSize = reqTaskMaxPoolSize;
	}

	public long getReqTaskKeepAliveTime() {
		return executorKeepAliveTime;
	}

	public void setReqTaskKeepAliveTime(long reqTaskKeepAliveTime) {
		this.executorKeepAliveTime = reqTaskKeepAliveTime;
	}

	public int getReqTaskTaskQueueSize() {
		return executorTaskQueueSize;
	}

	public void setReqTaskTaskQueueSize(int reqTaskTaskQueueSize) {
		this.executorTaskQueueSize = reqTaskTaskQueueSize;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public String getCharset() {
		return charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
