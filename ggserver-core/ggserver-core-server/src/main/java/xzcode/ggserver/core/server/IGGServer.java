package xzcode.ggserver.core.server;

import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.server.config.GGServerConfig;

/**
 * ggserver服务器接口
 * 
 * @author zai 2019-12-05 10:41:22
 */
public interface IGGServer extends IGGConfigSupport<GGServerConfig> , IGGContolSupport{

	/**
	 * 启动服务器
	 * 
	 * @author zai 2019-12-05 10:41:09
	 */
	IGGFuture start();

	/**
	 * 关闭服务器
	 * 
	 * @author zai 2019-12-05 10:41:16
	 */
	void shutdown();

}