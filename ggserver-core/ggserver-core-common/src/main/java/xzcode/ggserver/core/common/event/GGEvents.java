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
		String READE = "ggevents.idle.read";
	    /**
	     * 写空闲
	     */
		String WRITE = "ggevents.idle.write";
	    /**
	     * 读与写空闲
	     */
		String ALL = "ggevents.idle.all";

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
		 * 已连接
		 */
		String CONNECTED = "ggevents.conn.connected";
		
		/**
		 * 连接激活
		 */
		String ACTIVE = "ggevents.conn.active";
		
		/**
		 * 连接未激活
		 */
		String INACTIVE = "ggevents.conn.inactive";
		
		/**
		 * 连接已断开
		 */
		String DISCONNECTED = "ggevents.conn.disconnected";
		
	    /**
	     * 连接已关闭
	     */
		String CLOSED = "ggevents.conn.closed";

	}

}
