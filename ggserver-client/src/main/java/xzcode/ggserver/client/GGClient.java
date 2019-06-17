package xzcode.ggserver.client;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.event.EventRunnableInvoker;
import xzcode.ggserver.client.event.GGClientEvents;
import xzcode.ggserver.client.event.GGEventTask;
import xzcode.ggserver.client.event.IEventInvoker;
import xzcode.ggserver.client.executor.GGTaskExecutor;
import xzcode.ggserver.client.executor.task.TimeoutRunnable;
import xzcode.ggserver.client.executor.timeout.IGGTaskExecution;
import xzcode.ggserver.client.message.receive.IOnMessageAction;
import xzcode.ggserver.client.message.receive.invoker.OnMessagerInvoker;
import xzcode.ggserver.client.starter.impl.SocketClientStarter;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class GGClient implements IGGTaskExecution{
	
	private GGClientConfig config;
	
	private SocketClientStarter starter;


	public GGClient(GGClientConfig config) {
		this.config = config;
		starter = new SocketClientStarter(config);
	}
	
	public void connect() {
		this.starter.run();
	}
	

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	public void disconnect(Object userId) {
		disconnect(userId, 0);
	}
	/**
	 * 延迟断开连接
	 * 
	 * @param userId
	 * @param delayMs 延迟时间毫秒
	 * @author zai
	 * 2019-04-17 11:18:43
	 */
	public void disconnect(Object userId, long delayMs) {
		Channel channel = config.getChannel();
		if (channel != null) {
			if (delayMs <= 0) {
				channel.close();
			}else {
				setTimeout(() -> {
					channel.close();
				}, delayMs);
			}
		}
	}

	/**
	 * 断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	public void disconnect() {
		disconnect(null, 0);
	}
	
	/**
	 * 延迟断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	public void disconnect(long delayMs) {
		disconnect(null, delayMs);
	}
	
	/**
	 * 动态监听消息
	 * 
	 * @param string
	 * @param socketOnMessage
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	public <T> void on(String requestTag, IOnMessageAction<T> socketOnMessage) {
		
		
		OnMessagerInvoker<T> invoker = new OnMessagerInvoker<>();
		invoker.setOnMessage(socketOnMessage);
		invoker.setRequestTag(requestTag);
		String typeName = ((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
		Class<?> msgClass = null;
		if (typeName.startsWith("java.util.Map")) {
			msgClass = Map.class;
		}else {
			msgClass = (Class<?>) ((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];			
		}
		//获取泛型类型参数
		
		invoker.setRequestMessageClass(msgClass);
		
		config.getMessageInvokerManager().put(requestTag, invoker);
	}
	
	/**
	 * 动态添加事件监听
	 * 
	 * @param eventTag
	 * @param runnable
	 * @author zai
	 * 2019-01-02 20:02:37
	 */
	public <T> void onEvent(String eventTag, Runnable runnable) {
		IEventInvoker eventInvoker = config.getEventInvokerManager().get(eventTag);
		if (eventInvoker != null) {
			((EventRunnableInvoker)eventInvoker).addRunnable(runnable);
			return;
		}
		EventRunnableInvoker invoker = new EventRunnableInvoker();
		invoker.setEventTag(eventTag);
		invoker.addRunnable(runnable);
		config.getEventInvokerManager().put(invoker);
	}

	
	
	public void emitEvent(String eventTag, Object message) {
		config.getWorkerGroup().submit(new GGEventTask(eventTag, message, config));
	}
	
	public void emitEvent(String eventTag) {
		config.getWorkerGroup().submit(new GGEventTask(eventTag, null, config));
	}
	
	@Override
	public ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec) {
		return config.getWorkerGroup().schedule(runnable, timeoutMilliSec, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture<?> setTimeout(TimeoutRunnable runnable, long timeoutMilliSec) {
		ScheduledFuture<?> future = config.getWorkerGroup().schedule(runnable, timeoutMilliSec, TimeUnit.MILLISECONDS);
		return future;
	}
	
	public Future<?> submitTask(Runnable task) {
		return config.getWorkerGroup().submit(task);
	}
	
	public void sendBytes(byte[] bytes) {
		this.config.getSendMessageManager().sendBytes(bytes);
	}
	
	public void send(String action) {
		this.config.getSendMessageManager().send(action, null);
	}


	public void send(String action, Object message) {
		this.config.getSendMessageManager().send(action, message);
		
	}
	
	public void send(String action, long delayMs) {
		this.config.getSendMessageManager().send(action, delayMs);
		
	}
	public void send(String action, Object message, long delayMs) {
		this.config.getSendMessageManager().send(action, message, delayMs);
		
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(GGClient.class);
		AtomicInteger  count = new AtomicInteger(0);
		int times = 3000;
		List<Thread> list = new ArrayList<>(times);
		for (int i = 0; i < times; i++) {
			list.add(new Thread(() -> {
				GGClientConfig config = new GGClientConfig();
				config.setHost("192.168.8.219");
				config.setPort(9999);
				config.setAutoRun(true);
				GGClient client = new GGClient(config);
				client.onEvent(GGClientEvents.ConnectionState.SUCCESS, () -> {
					System.out.println("connect success!");
					HashMap<String, Object> data = new HashMap<>();
					data.put("token", "ec456828-faf1-450e-9c20-ed73e85f1050");
					client.send("login.req", data);
				});
				client.onEvent(GGClientEvents.ConnectionState.CLOSE, () -> {
					System.out.println("close");
				});
				client.on("login.resp", new IOnMessageAction<Map<String, Object>>(){
		
					@Override
					public void onMessage(Map<String, Object> data) {
						logger.debug("onMessage",data);
					}
					
				});
				client.connect();
			}));
		}
		
		for (Thread thread : list) {
			thread.start();
		}
		
		
		
	}
	
}
