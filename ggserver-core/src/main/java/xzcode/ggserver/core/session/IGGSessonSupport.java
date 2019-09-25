package xzcode.ggserver.core.session;

import xzcode.ggserver.core.config.IGGConfigSupport;

public interface IGGSessonSupport extends IGGConfigSupport{

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
	
	/**
	 * 获取指定用户的session
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-19 15:50:11
	 */
	default GGSession getSession(Object userId) {
		return getConfig().getUserSessonManager().get(userId);
	}
}
