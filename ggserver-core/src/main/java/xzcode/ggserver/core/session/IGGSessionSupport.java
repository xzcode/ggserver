package xzcode.ggserver.core.session;

import xzcode.ggserver.core.config.IGGConfigSupport;

public interface IGGSessionSupport extends IGGConfigSupport{

	/**
	 * 获取当前会话session对象
	 * 
	 * @return
	 * 
	 * @author zai 2017-08-04
	 */
	default GGSession getSession() {
		return GGSessionUtil.getSession();
	}
	
}
