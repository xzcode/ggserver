package xzcode.ggserver.core.common.event;

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
		String READE = "GG.idle.read";
	    /**
	     * 写空闲
	     */
		String WRITE = "GG.idle.write";
	    /**
	     * 读与写空闲
	     */
		String ALL = "GG.idle.all";

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
		String OPENED = "GG.conn.opened";
		
		/**
		 * 连接关闭
		 */
		String CLOSED = "GG.conn.closed";
		

	}

}
