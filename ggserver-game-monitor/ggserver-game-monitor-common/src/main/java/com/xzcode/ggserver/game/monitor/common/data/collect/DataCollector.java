package com.xzcode.ggserver.game.monitor.common.data.collect;

/**
 * 信息收集器统一接口
 *
 * @param <T>
 * @author zai
 * 2020-04-21 15:57:37
 */
public interface DataCollector<T> {

	/**
	 * 进行收集
	 *
	 * @return
	 * @author zai
	 * 2020-04-21 15:57:50
	 */
	T collect();
	
}
