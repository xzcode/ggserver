package com.xzcode.ggserver.core.common.utils.logger.filter;

import com.xzcode.ggserver.core.common.message.Pack;

/**
 * 包日志过滤器
 *
 * @author zai
 * 2020-04-11 15:45:30
 */
public interface PackLogFilter {
	
	/**
	 * 过滤处理
	 *
	 * @param pack
	 * @return
	 * @author zai
	 * 2020-04-11 15:45:41
	 */
	boolean filter(Pack pack);

}
