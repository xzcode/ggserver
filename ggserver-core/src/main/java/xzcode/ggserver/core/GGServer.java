package xzcode.ggserver.core;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.event.EventRunnableInvoker;
import xzcode.ggserver.core.event.GGEventTask;
import xzcode.ggserver.core.event.IEventInvoker;
import xzcode.ggserver.core.executor.task.TimeoutRunnable;
import xzcode.ggserver.core.executor.timeout.IGGServerExecution;
import xzcode.ggserver.core.handler.serializer.impl.JsonSerializer;
import xzcode.ggserver.core.handler.serializer.impl.MessagePackSerializer;
import xzcode.ggserver.core.message.receive.IOnMessageAction;
import xzcode.ggserver.core.message.receive.RedirectMessageTask;
import xzcode.ggserver.core.message.receive.invoker.OnMessagerInvoker;
import xzcode.ggserver.core.message.send.ISendMessage;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class GGServer implements ISendMessage, IGGServerExecution{
	
	private GGServerConfig config;
	

	public GGServer(GGServerConfig serverConfig) {
		this.config = serverConfig;
	}
	public GGServerConfig getServerConfig() {
		return config;
	}

	/**
	 * 获取当前会话session对象
	 * 
	 * @return
	 * 
	 * @author zai 2017-08-04
	 */
	public GGSession getSession() {
		return GGSessionThreadLocalUtil.getSession();
	}
	
	/**
	 * 获取指定用户的session
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-19 15:50:11
	 */
	public GGSession getSession(Object userId) {
		return this.config.getUserSessonManager().get(userId);
	}

	/**
	 * 把用户绑定到当前通信会话
	 * 
	 * @param userId
	 * 
	 * @author zai 2017-08-04
	 */
	public void userRegister(Object userId) {
		GGSession session = GGSessionThreadLocalUtil.getSession();

		session.register(userId);

		// 已注册会话绑定
		this.config.getUserSessonManager().put(userId, session);
	}

	/**
	 * 判断是否已登录
	 * 
	 * @param userId
	 * @author zai 2018-12-29 10:22:11
	 */
	public boolean isRegistered() {
		GGSession session = GGSessionThreadLocalUtil.getSession();
		return session.getRegisteredUserId() != null;
	}

	/**
	 * 把用户从注册绑定中移除
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:09:56
	 */
	public GGSession userUnregister(Object userId) {
		GGSession session = GGSessionThreadLocalUtil.getSession();

		session.unregister();

		// 注销会话绑定
		this.config.getUserSessonManager().remove(userId);
		
		return session;
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
		
		GGSession session = null;
		if (userId == null) {
			session = getSession();
		}else {
			session = this.config.getUserSessonManager().get(userId);			
		}
		if (session != null && session.getChannel() != null) {
			Channel channel = session.getChannel();
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
	 * @param onMessageAction
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	public <T> void on(String actionId, IOnMessageAction<T> onMessageAction) {
		
		OnMessagerInvoker<T> invoker = new OnMessagerInvoker<>();
		invoker.setOnMessage(onMessageAction);
		invoker.setRequestTag(actionId);
		String typeName = ((ParameterizedType)onMessageAction.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
		Class<?> msgClass = null;
		//获取泛型类型参数
		if (typeName.startsWith("java.util.Map")) {
			msgClass = Map.class;
		}else {
			msgClass = (Class<?>) ((ParameterizedType)onMessageAction.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];			
		}
		invoker.setRequestMessageClass(msgClass );
		
		config.getMessageInvokerManager().put(actionId, invoker);
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
	
	/**
	 * 发送自定义事件
	 * 
	 * @param eventTag
	 * @param message
	 * @author zai
	 * 2019-06-23 18:15:48
	 */
	public void emitEvent(String eventTag, Object message) {
		config.getTaskExecutor().submit(new GGEventTask(getSession(), eventTag, message, config));
	}
	
	/**
	 * 发送自定义事件
	 * 
	 * @param eventTag
	 * @author zai
	 * 2019-06-23 18:16:17
	 */
	public void emitEvent(String eventTag) {
		config.getTaskExecutor().submit(new GGEventTask(getSession(), eventTag, null, config));
	}
	
	/**
	 * 重定向消息
	 * 
	 * @param action
	 * @param message
	 * @author zai
	 * 2019-06-23 18:15:41
	 */
	public void redirect(String action, Object message) {
		this.config.getTaskExecutor().submit(new RedirectMessageTask(action, message, GGSessionThreadLocalUtil.getSession(), config));
	}
	
	@Override
	public ScheduledFuture<?> setTimeout(Runnable runnable, long timeoutMilliSec) {
		return this.config.getTaskExecutor().schedule(runnable, timeoutMilliSec, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture<?> setTimeout(TimeoutRunnable runnable, long timeoutMilliSec) {
		ScheduledFuture<?> future = this.config.getTaskExecutor().schedule(runnable, timeoutMilliSec, TimeUnit.MILLISECONDS);
		runnable.setTimeoutFuture(future);
		return future;
	}
	
	public Future<?> submitTask(Runnable task) {
		return this.config.getTaskExecutor().submit(task);
	}
	
	@Override
	public void send(Object userId, String action, Object message) {
		this.config.getSendMessageManager().send(userId, action, message);;
		
	}



	@Override
	public void send(Object userId, String action) {
		this.config.getSendMessageManager().send(userId, action);
		
	}



	@Override
	public void send(String action) {
		this.config.getSendMessageManager().send(action);
		
	}


	@Override
	public void send(String action, Object message) {
		this.config.getSendMessageManager().send(action, message);
		
	}
	
	@Override
	public void send(Object userId, String action, Object message, long delayMs) {
		this.config.getSendMessageManager().send(userId, action, message, delayMs);
		
	}
	@Override
	public void send(Object userId, String action, long delayMs) {
		this.config.getSendMessageManager().send(userId, action, delayMs);
		
	}
	@Override
	public void send(String action, long delayMs) {
		this.config.getSendMessageManager().send(action, delayMs);
		
	}
	@Override
	public void send(String action, Object message, long delayMs) {
		this.config.getSendMessageManager().send(action, message, delayMs);
		
	}
	@Override
	public void sendToAll(String action, Object message) {
		this.config.getSendMessageManager().send(action, message);
	}
	@Override
	public void sendToAll(String action) {
		this.config.getSendMessageManager().sendToAll(action);
	}
	
	
	public static void main(String[] args) throws Exception{
		int datasize = 100;
		MessagePackSerializer messagePackSerializer = new MessagePackSerializer();
		JsonSerializer jsonSerializer = new JsonSerializer();
		int times = 10 * 10000;
		ArrayList<SerTestModel> list = new ArrayList<>();
		for (int i = 0; i < times; i++) {
			ArrayList<Integer> handcards = new ArrayList<>();
			handcards.add(123);
			handcards.add(234);
			handcards.add(345);
			handcards.add(456);
			handcards.add(567);
			list.add(new SerTestModel("name"+i, i, i, handcards , UUID.randomUUID().toString(), 100000+i+1 + "", System.currentTimeMillis(), "is ok haha " + i, i % 2 == 0));
		}
		
		long starttime = 0;
		long endtime = 0;
		
		messagePackSerializer.serialize(list.get(0));
		jsonSerializer.serialize(list.get(0));
		
		
		
		starttime = System.currentTimeMillis();
		for (SerTestModel data : list) {
			//messagePackSerializer.serialize(data);	
			messagePackSerializer.deserialize(jsonSerializer.serialize(data), SerTestModel.class);		
		}
		endtime = System.currentTimeMillis() - starttime;
		
		System.out.println(endtime);
		
		starttime = System.currentTimeMillis();
		for (SerTestModel data : list) {
			jsonSerializer.deserialize(jsonSerializer.serialize(data), SerTestModel.class);			
		}
		endtime = System.currentTimeMillis() - starttime;
		
		System.out.println(endtime);
	}
	
	
	public static class SerTestModel {
		private String name;
		private Integer age;
		private Integer seatType;
		private ArrayList<Integer> handcards;
		private String roundNo;
		private String roomNo;
		private Long time;
		private String message;
		private boolean success;
		
		public SerTestModel() {}
		
		public SerTestModel(String name, int age, Integer seatType, ArrayList<Integer> handcards, String roundNo,
				String roomNo, long time, String message, boolean success) {
			super();
			this.name = name;
			this.age = age;
			this.seatType = seatType;
			this.handcards = handcards;
			this.roundNo = roundNo;
			this.roomNo = roomNo;
			this.time = time;
			this.message = message;
			this.success = success;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Integer getSeatType() {
			return seatType;
		}
		public void setSeatType(Integer seatType) {
			this.seatType = seatType;
		}
		public ArrayList<Integer> getHandcards() {
			return handcards;
		}
		public void setHandcards(ArrayList<Integer> handcards) {
			this.handcards = handcards;
		}
		public String getRoundNo() {
			return roundNo;
		}
		public void setRoundNo(String roundNo) {
			this.roundNo = roundNo;
		}
		public String getRoomNo() {
			return roomNo;
		}
		public void setRoomNo(String roomNo) {
			this.roomNo = roomNo;
		}
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
		
	}

}
