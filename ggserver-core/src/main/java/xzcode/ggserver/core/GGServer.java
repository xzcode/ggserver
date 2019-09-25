package xzcode.ggserver.core;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.config.IGGConfigSupport;
import xzcode.ggserver.core.control.IGGContolSupport;
import xzcode.ggserver.core.event.IEventInvokeSupport;
import xzcode.ggserver.core.executor.IExecutorSupport;
import xzcode.ggserver.core.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.message.send.ISendMessageSupport;
import xzcode.ggserver.core.message.send.SendMessageManager;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionUtil;
import xzcode.ggserver.core.session.IGGSessonSupport;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class GGServer 
implements 
	IGGConfigSupport,
	ISendMessageSupport, 
	IRequestMessageSupport,
	IExecutorSupport, 
	IGGSessonSupport, 
	IEventInvokeSupport,
	IGGContolSupport
{
	
	private GGConfig config;
	

	public GGServer(GGConfig serverConfig) {
		this.config = serverConfig;
	}
	public GGConfig getConfig() {
		return config;
	}


	/**
	 * 把用户绑定到当前通信会话
	 * 
	 * @param userId
	 * 
	 * @author zai 2017-08-04
	 */
	public void userRegister(Object userId) {
		GGSession session = GGSessionUtil.getSession();

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
		GGSession session = GGSessionUtil.getSession();
		return session.getRegisteredUserId() != null;
	}

	/**
	 * 把用户从注册绑定中移除
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:09:56
	 */
	public GGSession userUnregister(Object userId) {
		GGSession session = GGSessionUtil.getSession();

		session.unregister();

		// 注销会话绑定
		this.config.getUserSessonManager().remove(userId);
		
		return session;
	}

	@Override
	public SendMessageManager getSendMessageManager() {
		return this.config.getSendMessageManager();
	}
	@Override
	public NioEventLoopGroup getTaskExecutor() {
		return this.config.getTaskExecutor();
	}
	
	

}
