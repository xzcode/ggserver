package xzcode.ggserver.core.common.session.support;

import xzcode.ggserver.core.common.session.GGSession;

public interface IGGSessionSupport{

	/**
	 * 获取当前会话session对象
	 * 
	 * @return
	 * 
	 * @author zai 2017-08-04
	 */
	GGSession getSession();
	
}
