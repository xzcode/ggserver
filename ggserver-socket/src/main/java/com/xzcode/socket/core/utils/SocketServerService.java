package com.xzcode.socket.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.xzcode.socket.core.channel.SocketChannelGroups;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.event.EventRunnableInvoker;
import com.xzcode.socket.core.event.IEventInvoker;
import com.xzcode.socket.core.message.MessageInvokerManager;
import com.xzcode.socket.core.message.SocketOnMessage;
import com.xzcode.socket.core.message.invoker.OnMessagerInvoker;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.session.SocketSessionUtil;
import com.xzcode.socket.core.session.UserSessonManager;
import com.xzcode.socket.core.session.imp.SocketSession;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class SocketServerService {
	
	private SocketServerConfig serverConfig;
	
	


	public SocketServerService(SocketServerConfig serverConfig) {
		super();
		this.serverConfig = serverConfig;
	}

	/**
	 * 发送消息
	 * 
	 * @param userId
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-08-04
	 */
	public void send(Object userId, String sendTag, Object message) {
		SocketSession session = this.serverConfig.getUserSessonManager().get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message));
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param userId          用户id
	 * @param sendTag         发送消息标识
	 * @param message         消息体
	 * @param runnable 发送完成回调
	 * @author zai 2018-12-29 14:28:10
	 */
	public void send(Object userId, String sendTag, Object message, Runnable runnable) {
		SocketSession session = this.serverConfig.getUserSessonManager().get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, runnable));
		}
	}

	/**
	 * 根据用户id发送消息（无消息体）
	 * 
	 * @param userId  用户id
	 * @param sendTag 发送消息标识
	 * @author zai 2018-12-29 14:25:27
	 */
	public void send(Object userId, String sendTag) {
		SocketSession session = this.serverConfig.getUserSessonManager().get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null));
		}
	}

	/**
	 * 根据用户id发送消息（无消息体）
	 * 
	 * @param userId          用户id
	 * @param sendTag         发送消息标识
	 * @param runnable 发送完成回调
	 * @author zai 2018-12-29 14:26:54
	 */
	public void send(Object userId, String sendTag, Runnable runnable) {
		SocketSession session = this.serverConfig.getUserSessonManager().get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, runnable));
		}
	}

	/**
	 * 发送消息到当前通道
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-18
	 */
	public void send(String sendTag, Object message) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message));
		}
	}

	/**
	 * 发送消息到当前通道
	 * 
	 * @param sendTag
	 * @param message
	 * @param runnable 发送完成回调
	 * @author zai 2018-12-29 14:24:27
	 */
	public void send(String sendTag, Object message, Runnable runnable) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, runnable));
		}
	}

	/**
	 * 发送消息（无消息体）
	 * 
	 * @param sendTag
	 * @param runnable 发送完成回调
	 * @author zai 2018-12-29 14:23:18
	 */
	public void send(String sendTag, Runnable runnable) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, runnable));
		}
	}

	/**
	 * 发送消息（无消息体）
	 * 
	 * @param sendTag
	 * @author zai 2018-12-29 14:23:54
	 */
	public void send(String sendTag) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null));
		}
	}

	/**
	 * 发送消息到所有channel
	 * 
	 * @param sendTag
	 * 
	 * @author zai 2017-09-21
	 */
	public void sendGobal(String sendTag) {
		SocketChannelGroups.getGlobalGroup()
				.writeAndFlush(SendModel.create(sendTag, null));
	}

	/**
	 * 发送消息到所有channel
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-21
	 */

	public void sendGobal(String sendTag, Object message) {
		SocketChannelGroups.getGlobalGroup()
				.writeAndFlush(SendModel.create(sendTag, message));
	}

	/**
	 * 发送消息到所有已登录的channel
	 * 
	 * @param sendTag
	 * 
	 * @author zai 2017-09-21
	 */
	public void sendToAllRegistered(String sendTag) {
		SocketChannelGroups.getRegisteredGroup()
				.writeAndFlush(SendModel.create(sendTag, null));
	}

	/**
	 * 发送消息到所有已登录的channel
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-21
	 */

	public void sendToAllRegistered(String sendTag, Object message) {
		SocketChannelGroups.getRegisteredGroup()
				.writeAndFlush(SendModel.create(sendTag, message));
	}

	/**
	 * 获取当前会话session对象
	 * 
	 * @return
	 * 
	 * @author zai 2017-08-04
	 */
	public SocketSession getSession() {
		return SocketSessionUtil.getSession();
	}
	
	/**
	 * 获取指定用户的session
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-19 15:50:11
	 */
	public SocketSession getSession(Object userId) {
		return this.serverConfig.getUserSessonManager().get(userId);
	}

	/**
	 * 把用户绑定到当前通信会话
	 * 
	 * @param userId
	 * 
	 * @author zai 2017-08-04
	 */
	public void userRegister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();

		session.setRegisteredUserId(userId);

		// 已注册会话绑定
		this.serverConfig.getUserSessonManager().put(userId,session);
		// 已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().add(session.getChannel());
	}

	/**
	 * 判断是否已登录
	 * 
	 * @param userId
	 * @author zai 2018-12-29 10:22:11
	 */
	public boolean isRegistered() {
		SocketSession session = SocketSessionUtil.getSession();
		return session.getRegisteredUserId() != null;
	}

	/**
	 * 把用户从注册绑定中移除
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:09:56
	 */
	public SocketSession userUnregister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();

		session.unregister();

		// 注销会话绑定
		this.serverConfig.getUserSessonManager().remove(userId);

		// 已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().remove(session.getChannel());

		return session;
	}

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	public void disconnect(Object userId) {
		SocketSession session = this.serverConfig.getUserSessonManager().get(userId);
		if (session != null && session.getChannel() != null) {
			session.getChannel().close();
		}
	}

	/**
	 * 断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	public void disconnect() {
		getSession().getChannel().close();
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
	public <T> void on(String requestTag, SocketOnMessage<T> socketOnMessage) {
		
		
		OnMessagerInvoker<T> invoker = new OnMessagerInvoker<>();
		invoker.setOnMessage(socketOnMessage);
		invoker.setRequestTag(requestTag);
		//获取泛型类型参数
		Class<?> msgClass = (Class<?>) ((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
		invoker.setRequestMessageClass(msgClass );
		
		/*	
		System.out.println("接口是否是泛型类："+ (socketOnMessage.getClass().getGenericInterfaces()[0] instanceof ParameterizedType));
        System.out.println("泛型类的名称："+ socketOnMessage.getClass().getGenericInterfaces()[0].getTypeName());
        System.out.println("泛型类的实现："+ socketOnMessage.getClass().getGenericInterfaces()[0].getClass());
        System.out.println("是否是泛型参数:"+(((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0] instanceof TypeVariable));
        System.out.println("泛型参数名称:"+((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName());
        System.out.println("泛型参数的实现："+((ParameterizedType)socketOnMessage.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getClass());
        */
		serverConfig.getMessageInvokerManager().put(requestTag, invoker);
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
		IEventInvoker eventInvoker = serverConfig.getEventInvokerManager().get(eventTag);
		if (eventInvoker != null) {
			((EventRunnableInvoker)eventInvoker).addRunnable(runnable);
			return;
		}
		EventRunnableInvoker invoker = new EventRunnableInvoker();
		invoker.setEventTag(eventTag);
		invoker.addRunnable(runnable);
		serverConfig.getEventInvokerManager().put(invoker);
	}

}
