package com.xzcode.ggserver.core.common.config;

import java.nio.charset.Charset;

import com.xzcode.ggserver.core.common.event.EventManager;
import com.xzcode.ggserver.core.common.event.EventSupport;
import com.xzcode.ggserver.core.common.executor.TaskExecutor;
import com.xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import com.xzcode.ggserver.core.common.filter.FilterManager;
import com.xzcode.ggserver.core.common.filter.FilterSupport;
import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.message.request.manager.ReceiveMessageManager;
import com.xzcode.ggserver.core.common.message.request.support.ReceiveMessageSupport;
import com.xzcode.ggserver.core.common.message.response.support.SendMessageSupport;
import com.xzcode.ggserver.core.common.session.manager.ISessionManager;

/**
 * 配置获取支持
 * 
 * @param <C>
 * @author zai 2019-12-11 10:11:31
 */
public interface GGConfigSupport<C extends GGConfig> 
extends
SendMessageSupport, 
ReceiveMessageSupport, 
FilterSupport, 
IExecutorSupport, 
EventSupport

{

	/**
	 * 获取配置对象
	 * 
	 * @return
	 * @author zai 2019-12-11 10:12:59
	 */
	C getConfig();

	@Override
	default ISerializer getSerializer() {
		return getConfig().getSerializer();
	}
	@Override
	default EventManager getEventManagerImpl() {
		return getConfig().getEventManager();
	}
	@Override
	default TaskExecutor getTaskExecutor() {
		return getConfig().getTaskExecutor();
	}
	@Override
	default ReceiveMessageManager getRequestMessageManager() {
		return getConfig().getRequestMessageManager();
	}

	default ISessionManager getSessionManager() {
		return getConfig().getSessionManager();
	}
	@Override
	default FilterManager getFilterManager() {
		return getConfig().getFilterManager();
	}
	@Override
	default Charset getCharset() {
		return getConfig().getCharset();
	}

}
