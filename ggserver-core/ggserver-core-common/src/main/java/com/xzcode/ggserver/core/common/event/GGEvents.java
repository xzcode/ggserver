package com.xzcode.ggserver.core.common.event;

/**
 * ggserver默认事件
 * 
 * @author zai
 * 2019-10-22 15:51:51
 */
public interface GGEvents {
	
	/**
	 * 空闲状态 事件
	 * 
	 * 
	 * @author zai
	 * 2017-08-03
	 */
	interface Idle {
		
		/**
	     * 读空闲
	     */
		String READE = "GG.IDLE.READ";
	    /**
	     * 写空闲
	     */
		String WRITE = "GG.IDLE.WRITE";
	    /**
	     * 读与写空闲
	     */
		String ALL = "GG.IDLE.ALL";

	}
	
	
	/**
	 * 连接状态
	 * 
	 * 
	 * @author zai
	 * 2017-09-25
	 */
	interface Connection {
		
		/**
		 * 连接打开
		 */
		String OPENED = "GG.CONN.OPENED";
		
		/**
		 * 连接关闭
		 */
		String CLOSED = "GG.CONN.CLOSED";

	}
	
	/**
	 * 会话相关事件
	 * 
	 * @author zai
	 * 2019-12-27 11:59:28
	 */
	interface Session {
		
		/**
		 * 会话超时
		 */
		String EXPIRED = "GG.SESSION.EXPIRED";
		

	}

}
