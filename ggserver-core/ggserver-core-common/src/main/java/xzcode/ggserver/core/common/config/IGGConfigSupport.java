package xzcode.ggserver.core.common.config;

import java.nio.charset.Charset;

import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.event.IEventSupport;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.IFilterSupport;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.request.manager.IRequestMessageManager;
import xzcode.ggserver.core.common.message.request.support.IRequestMessageSupport;
import xzcode.ggserver.core.common.message.response.support.ISendMessageSupport;
import xzcode.ggserver.core.common.session.manager.ISessionManager;

/**
 * 配置获取支持
 * 
 * @param <C>
 * @author zai 2019-12-11 10:11:31
 */
public interface IGGConfigSupport<C extends GGConfig> extends

		ISendMessageSupport, IRequestMessageSupport, IFilterSupport, IExecutorSupport, IEventSupport

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
	default IEventManager getEventManagerImpl() {
		return getConfig().getEventManager();
	}
	@Override
	default ITaskExecutor getTaskExecutor() {
		return getConfig().getTaskExecutor();
	}
	@Override
	default IRequestMessageManager getRequestMessageManager() {
		return getConfig().getRequestMessageManager();
	}

	default ISessionManager getSessionManager() {
		return getConfig().getSessionManager();
	}
	@Override
	default IFilterManager getFilterManager() {
		return getConfig().getFilterManager();
	}
	@Override
	default Charset getCharset() {
		return getConfig().getCharset();
	}

}
